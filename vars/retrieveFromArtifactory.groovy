
def call(globalConfig, artifactoryURL, workspace, fileName) {
	def curlCommand = "${globalConfig.CURL} ${globalConfig.ARTIFACTORY_BASE_URL}/${artifactoryURL} -o \"${workspace}/${fileName}\""
	bat "${curlCommand}"
}