import com.hastingsdirect.sql.RepositoryBuilds

def call(List appServerIds=[]) {
	unstash name: "ps_scripts"
	powershellScriptDir="Powershell Scripts"
	
	RepositoryBuilds repoBuilds=new RepositoryBuilds()
	def uniqueServerIds=appServerIds.unique()
	
	def restartJobs=[:]
	
	uniqueServerIds.each({
		def appServer = repoBuilds.getAppserverByID(it)
		def server = repoBuilds.getServerByID(appServer.server_id)
		def credential = repoBuilds.getCredentialByID(appServer.credential_id)
		restartJobs['Restart_'+server.name]= {
			echo "Restart Server ${server.name}"
			createRemoteServerRestartJob(appServer,server,credential)
			eventsStore(msg:"Restarted server ${server.name}:${appServer.name}")
		}
	})
	parallel(restartJobs)
}

def checkRemoteServerServiceStatus(credential,remoteServiceName, remoteServer) {
	def serviceStatusBool = false
	def tmpOutFile = "${workspace}\\tmpOutFile.properties"
	println 'WORKSPACE:'+workspace 
	
	try {
		retry(3) {
			println("Testing remote service [${remoteServiceName}] on server [${remoteServer}].")
			withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: credential, passwordVariable: 'PWORD', usernameVariable: 'UNAME']]) { bat("@powershell -File \"${powershellScriptDir}\\serviceStatusToFileWithCred.ps1\" -SERVER ${remoteServer} -SERVICE \"${remoteServiceName}\" -TMP_OUT_FILE \"${tmpOutFile}\" -USER ${UNAME} -PASSWORD ${PWORD}") }
			def tmpServiceProp = readProperties file: tmpOutFile
			serviceStatus = tmpServiceProp['servicestatus']
			tmpServiceProp = null

			if ("Running".equals(serviceStatus)) {
				serviceStatusBool = true
				println("Remote service [${remoteServiceName}] is [${serviceStatus}].")
				return serviceStatusBool
			} else {
				throw new hudson.AbortException("ERROR: Failure in checkRemoteServerServiceStatus.")
			}
		}
	} catch (Exception e) {
		println("Maximum number of attemps to test remote service [${remoteServiceName}] reached.")
	}
	return serviceStatusBool
}

def createRemoteServerRestartJob(appServer,server,credential) {
	script{		
		def serverName = server.name
		def serverDomain = server.domain
		def remoteServer = "${serverName}.${serverDomain}"
		def remoteServiceName = appServer.servicename
		def profileName = appServer.name
		def remoteProfileRoot = appServer.profileroot
		def remoteUser = credential.username
		def remotePassword = credential.password

		def serviceStatusBool = false
		serviceStatusBool = checkRemoteServerServiceStatus("SVC_TST_Autodeploy", remoteServiceName, remoteServer)
		lock("${serverName}_${profileName}") {
			withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: "SVC_TST_Autodeploy", passwordVariable: 'PWORD', usernameVariable: 'UNAME']]) {
				echo "Stopping WebSphere on [${remoteServer}]"
				bat "@powershell -F \"${powershellScriptDir}\\stopWebSphere.ps1\" -SERVER ${remoteServer} -PROFILE_ROOT ${remoteProfileRoot} -WAS_USER ${remoteUser} -WAS_PASSWORD ${remotePassword} -USER ${UNAME} -PASSWORD ${PWORD}"

				echo "Starting WebSphere on [${remoteServer}]"
				bat "@powershell -F \"${powershellScriptDir}\\startWebSphere.ps1\" -SERVER ${remoteServer} -PROFILE_ROOT ${remoteProfileRoot} -USER ${UNAME} -PASSWORD ${PWORD}"
			}
		}

		/*
		 *  Check that service is up and running after restart before proceeding
		 */
		serviceStatusBool = checkRemoteServerServiceStatus("SVC_TST_Autodeploy", remoteServiceName, remoteServer)
	}
}


