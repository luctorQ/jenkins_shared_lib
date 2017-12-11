import com.hastingsdirect.pipeline.PipelineUtils
import com.hastingsdirect.sql.RepositoryBuilds

def call(List buildInfos=[]) {

	def artifactoryDeployments=[:]
	buildInfos.each({build->
		artifactoryDeployments['Artifactory_upload_'+build.appname]= { uploadBuildToArtifactory(build) }
	})
	parallel(artifactoryDeployments)
}

def uploadBuildToArtifactory(buildInfo) {
	println 'buildInfo to upload:'+buildInfo

	if(!buildInfo.storedinartifactory) {
		RepositoryBuilds repoBuilds=new RepositoryBuilds()
		def globalConfig=PipelineUtils.getJenkinsConfiguration(env.COMPUTERNAME)

		def svnRev=buildInfo.svnrevisionnumber
		def exists=checkSvnRevisionInArtifactory(buildInfo.appname, svnRev)
		println('exist in artifactory:'+exists)

		def artifactName = PipelineUtils.getArtifactName(buildInfo.appname)
		def artifactoryUrl = "GuideWire/com/hastingsdirect/${buildInfo.appname}/${svnRev}/${artifactName}"
		def publishUrl = globalConfig.ARTIFACTORY_BASE_URL+'/'+artifactoryUrl

		if(exists) {
			//update CMDB only
			repoBuilds.updateBuildInfoArtifactoryDeployment(buildInfo.id, artifactoryUrl)
			eventsStore(msg:"Updated CMDB build ${buildInfo.id} with artifactory sync ${publishUrl}",
			type:'ARTIFACTORY_UPLOAD_CMDB',
			ref:[buildId:buildInfo.id,
				publishUrl:publishUrl,
				artifactName:artifactName])
			return
		}


		//store in artifactory
		def jenkinsArtifactNumber=buildInfo.jenkinsbuildnumber
		def artifactFile = "dist/ear/${artifactName}"
		def artifactPath="dist\\ear\\${artifactName}"
		def projectName=PipelineUtils.jenkinsBuildJobNameFromApp(buildInfo.appname)
		echo "buildNo:${jenkinsArtifactNumber}"
		echo "artifactFile:${artifactFile}"
		echo "projectName:${projectName}"
		try {
			step ([$class: 'CopyArtifact', projectName: projectName, filter: artifactFile, selector: [$class: 'SpecificBuildSelector', buildNumber: "${jenkinsArtifactNumber}"]])
		}catch(err) {
			println 'copy artifact error:'+err
		}
		println 'artifactory publish url:'+publishUrl


		withCredentials([
			[$class: 'UsernamePasswordMultiBinding', credentialsId: "ARTIFACTORY", passwordVariable: 'PWORD', usernameVariable: 'UNAME']
		]) {
			def uploadStatus
			dir("${workspace}/dist/ear") { uploadStatus=bat "${globalConfig.CURL} --silent -u${UNAME}:${PWORD} -T \"${artifactName}\" ${publishUrl}"  }
			if (uploadStatus =~ /errors/) {
				echo "Artifactory upload Error:"+uploadStatus
				eventsStore(msg:"Error during upload to artifactory application ${buildInfo.appname} to url ${publishUrl}",
				type:'ARTIFACTORY_UPLOAD',
				ref:[publishUrl:publishUrl,
					artifactName:artifactName,
					status:'UPLOAD_ERROR'])
			}
			else {
				//update CMDB
				repoBuilds.updateBuildInfoArtifactoryDeployment(buildInfo.id, artifactoryUrl)
				eventsStore(msg:"Uploaded to artifactory application ${buildInfo.appname} to url ${publishUrl}",
				type:'ARTIFACTORY_UPLOAD',
				ref:[publishUrl:publishUrl,
					artifactName:artifactName,
					status:'UPLOAD_OK'])
			}
		}
	}
}
