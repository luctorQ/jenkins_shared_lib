import com.hastingsdirect.pipeline.PipelineUtils

def call(appName,svnRevision) {
	def ARTIFACTORY_CREDS_ID='ARTIFACTORY'
	def artifactName=PipelineUtils.getArtifactName(appName)
	def globalConfig=PipelineUtils.getJenkinsConfiguration(env.COMPUTERNAME)
	
	def urlToCheck = "${globalConfig.ARTIFACTORY_BASE_URL}/api/storage/GuideWire/com/hastingsdirect/${appName}/${svnRevision}/${artifactName}"
	println("checkSvnRevisionInArtifactory. Checking [${urlToCheck}].")

	def response
	withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: ARTIFACTORY_CREDS_ID, passwordVariable: 'PWORD', usernameVariable: 'UNAME']]) {
		script{
			def checkCommand = "${globalConfig.CURL} --silent -u${UNAME}:${PWORD} -X GET ${urlToCheck}"
			response = bat (
					script: "${checkCommand}",
					returnStdout: true
					).trim()
		}
	}
	
	// Don't need to parse the json response - we can just string search for the errors element. It won't exist in successful queries.
	if (response =~ /errors/) {
		echo "We don't have this version already."
		return false
	}
	else {
		echo "This version exists in Artifactory."
		return true
	}
}