import com.hastingsdirect.sql.RepositoryBuilds
import com.hastingsdirect.pipeline.PipelineUtils


/*
 * [appserverId:[appname:buildId]]
 * ex deploy pc jenkinsbuild 609 on RM1 appserver environment [1:[pc:609]]
 */
def call(Map serverDeployments) {
	unstash name: "ps_scripts"
	unstash name: "was_scripts"

	credsID='SVC_TST_Autodeploy'

	powershellScriptDir="Powershell Scripts"
	wasScriptsDir = "WebSphere Scripts"
	RepositoryBuilds repoBuilds=new RepositoryBuilds()

	def deploymentJobs=[:]

	def allBuildsInfo=[:]
	serverDeployments.each({appserverId,buildidlist->
		def appServer=repoBuilds.getAppserverByID(appserverId)
		def server=repoBuilds.getServerByID(appServer.server_id)

		buildidlist.each({appname,buildId->
			def appBuildInfo=allBuildsInfo[appname]
			if(appBuildInfo==null) {
				allBuildsInfo[appname]=[:]
			}

			def buildInfo=allBuildsInfo[appname][buildId]
			if(buildInfo==null) {
				buildInfo=repoBuilds.getBuildByAppnameJenkinsBuildId(appname,buildId)
				allBuildsInfo[appname][buildId]=buildInfo
				prepareBuildArtefactsToDeploy(buildInfo)
			}
			def appConfig = repoBuilds.getAppOnServer(appname, appserverId)
			deploymentJobs[buildInfo.appname+'_Deploy_'+server.name+'_'+appServer.name]= {
				echo "Deploy ${buildInfo.appname} on ${server.name}"
				lock(PipelineUtils.lockAppServerName(server.name,appServer.name)) {
					createDeployApplicationJob(buildInfo,appServer,server,appConfig)
				}
			}
		})
	})

	return deploymentJobs
}

def prepareBuildArtefactsToDeploy(buildInfo) {
	echo "deployOnServer.prepareBuildArtefactsToDeploy buildInfo:${buildInfo}"
	script{
		def appArtifactName = PipelineUtils.getArtifactName(buildInfo.appname)
		if(!buildInfo.storedinartifactory) {
			def jenkinsArtifactNumber=buildInfo.jenkinsbuildnumber
			def artifactFile = "dist/ear/${appArtifactName}"
			def artifactPath="dist\\ear\\${appArtifactName}"
			def projectName=PipelineUtils.jenkinsBuildJobNameFromApp(buildInfo.appname)
			step ([$class: 'CopyArtifact', projectName: projectName, filter: artifactFile, selector: [$class: 'SpecificBuildSelector', buildNumber: "${jenkinsArtifactNumber}"]])
			//						def artifactExists = fileExists artifactFile
			//						if (!artifactExists) exitJobWithFailure("Artifact [${artifactFile}] for build [${response.number}] does not exist. artifactExists: [${artifactExists}].")
			stash name:"stashed_${appArtifactName}",includes:artifactFile
		}
	}
}




def createDeployApplicationJob(preparedBuildInfo,appServer,server,appConfig) {
	def psDeploymentErrorCodeDescriptions = [
		"1000":"WAS is not running.",
		"1010":"PS Drive not created correctly.",
		"1020":"Files not copied to remote machine correctly."
	]

	RepositoryBuilds repoBuilds=new RepositoryBuilds()
	//		unstash name: "was_scripts"
	//		unstash name: "ps_scripts"
	def globalConfig=PipelineUtils.getJenkinsConfiguration(env.COMPUTERNAME)


	def workspace = pwd()
	def websphereScriptsDir = "${workspace}\\WebSphere Scripts"
	def powershellScriptsDir = "${workspace}\\Powershell Scripts"
	def powershellDeploymentScript = "${powershellScriptsDir}/remoteDeploymentGuidewire.ps1"

	def machineEarStored = "${env.COMPUTERNAME}"
	println 'deployOnServer.createDeployApplicationJob machineEarStored:'+machineEarStored
	def earStoredDir=workspace

	def appArtifactName = PipelineUtils.getArtifactName(preparedBuildInfo.appname)
	def artifactPath=""
	if(preparedBuildInfo.storedinartifactory) {
		echo "${preparedBuildInfo.appname} deploy from artifactory"
		def artifactoryURL = preparedBuildInfo.artifactoryurl
		retrieveFromArtifactory(globalConfig, artifactoryURL, workspace, appArtifactName)
		artifactPath=appArtifactName
	}else {
		echo "${preparedBuildInfo.appname} deploy from jenkins job: ${preparedBuildInfo.jenkinsbuildnumber}"
		artifactPath="dist\\ear\\${appArtifactName}"
		unstash name:"stashed_${appArtifactName}"
		earStoredDir=workspace+'\\dist\\ear'
		//						def artifactExists = fileExists artifactFile
		//						if (!artifactExists) exitJobWithFailure("Artifact [${artifactFile}] for build [${response.number}] does not exist. artifactExists: [${artifactExists}].")
	}
	println 'app info:'+appConfig
	def credential = repoBuilds.getAppserverCredentialsByID(appServer.credential_id)
	def remoteServer = "${server.name}.${server.domain}"
	def remoteProfileRoot = appServer.profileroot
	def remoteUser = credential.username
	def remotePassword = credential.password
	def remotePort = appServer.port
	def remoteNode = appServer.nodename
	def remoteServiceName = appServer.servicename
	def enableSSO = appConfig.sso
	def jobName = "${server.name}_${appServer.name}"
	def wasParams = "-lang jython -conntype SOAP"
	def remoteHost = "-host ${remoteServer} -port ${remotePort}"
	def userPass = "-user ${remoteUser} -password ${remotePassword}"
	def wasScript = "-f \"${wasScriptsDir}/WebSphere_Deploy.py\""
	//		def scriptParams = "${app.name} \"${workspace}\\${appArtifactName}\" ${enableSSO}"
	def scriptParams = "${appConfig.name} \"${workspace}\\${artifactPath}\" ${enableSSO}"
	println "remoteServer:"+remoteServer
	println "remoteProfileRoot:"+remoteProfileRoot
	println "remoteUser:"+remoteUser
	//		println "remotePassword:"+remotePassword
	println "remotePort:"+remotePort
	println "remoteNode:"+remoteNode
	println "remoteServiceName:"+remoteServiceName
	println "enableSSO:"+enableSSO
	println "jobName:"+jobName
	println "wasParams:"+wasParams
	println "remoteHost:"+remoteHost
	//		println "userPass:"+userPass
	println "wasScript:"+wasScript
	println "scriptParams:"+scriptParams



	withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: credsID, passwordVariable: 'PWORD', usernameVariable: 'UNAME']]) {
		def additionalParams = "-scriptsDir \"${powershellScriptsDir}\" -remoteServer ${remoteServer} -remoteDir \"${appServer.profileroot}/${appServer.nodename}Cell\" -app ${appConfig.name} "
		additionalParams += "-revision ${preparedBuildInfo.buildidentifier} -user ${UNAME} -password ${PWORD} -serviceName \"${appServer.servicename}\" "
		additionalParams += "-localEarDir \"${earStoredDir}\" -localEarMachineName ${machineEarStored} -appEar ${appArtifactName} -profileRoot \"${appServer.profileroot}\" -nodeName ${appServer.nodename} "
		additionalParams += "-wasScriptDir \"${websphereScriptsDir}\" -enableSSO ${enableSSO} -wasUser ${credential.username} -wasPassword ${credential.password}"

		def psDeploymentCommand = "& \"${powershellDeploymentScript}\" ${additionalParams}"
		println 'psDeploymentCommand:'+psDeploymentCommand
		def psDeploymentExitCode = powershell returnStatus: true, script: psDeploymentCommand

		println 'psDeploymentExitCode:'+psDeploymentExitCode

		def buildUser=null
		def appID=appConfig.id
		//		wrap([$class: 'BuildUser']) { buildUser = env.BUILD_USER_ID }
		def deploymentDate = PipelineUtils.returnSQLDateTimeString()
		def absoluteUrl = currentBuild.absoluteUrl
		def buildID=preparedBuildInfo.id

		if ("${psDeploymentExitCode}" == "0") {
			println("Deployment for appID [${appID}], Jenkins build number [${currentBuild.id}] is successful.")
			println("Updating CMDB.")
			def result = repoBuilds.updateAppWithDeploymentInfo(buildID, deploymentDate, buildUser, absoluteUrl, appID)
			eventsStore(msg:"Deployed app ${preparedBuildInfo.appname} on server ${server.name}:${appServer.name}",
				type:"DEPLOYED_APP",
				ref:preparedBuildInfo)
		} else {
			//			currentBuild.result = "UNSTABLE"
			//			println("Setting current build status to unstable.")

			def errorCodeDesc = psDeploymentErrorCodeDescriptions["${psDeploymentExitCode}"]
			println("Deployment for appID [${appID}], Jenkins build number [${currentBuild.id}] is not successful.")
			println("Error code [${psDeploymentExitCode}] and description [${errorCodeDesc}].")
			eventsStore(msg:"Failed deployment for app ${preparedBuildInfo.appname} on server ${server.name}:${appServer.name}",
				type:"DEPLOYMENT_APP_FAILED",
				ref:preparedBuildInfo)
			throw new hudson.AbortException("ERROR: Failure in deployment of ${preparedBuildInfo.appname}")
		}
	}
}

