import com.hastingsdirect.sql.RepositoryBuilds
import com.hastingsdirect.pipeline.PipelineUtils

def call(List appServerIds=[]) {
	unstash name: "ps_scripts"
	unstash name: "was_scripts"
	
	powershellScriptDir="Powershell Scripts"
	wasScriptsDir = "WebSphere Scripts"
	
	
	RepositoryBuilds repoBuilds=new RepositoryBuilds()
	def uniqueServerIds=appServerIds.unique()
	
	def appStartJobs=[:]
	def appStartOrder=['bc', 'pc', 'cc', 'ab']
	uniqueServerIds.each({
		def appsOnServer=repoBuilds.getAllAppsOnAppserver(it).collect({it.name})
		println "appsOnServer ${it}:"+appsOnServer
		def appsToStartInOrder=appStartOrder.findAll({appsOnServer.contains(it)})
		println "appsToStartInOrder on server ${it}:"+appsToStartInOrder
		def appServer = repoBuilds.getAppserverByID(it)
		def server = repoBuilds.getServerByID(appServer.server_id)
		appStartJobs["start_apps_${server.name}"]= {
			lock(PipelineUtils.lockAppServerName(server.name, appServer.name)){
				appsToStartInOrder.each({
					startApplicationOnServer(it,appServer,server)
				})
			}
		}
	})
	
	return appStartJobs	
}

def startApplicationOnServer(appname,appServer,server) {
	RepositoryBuilds repoBuilds=new RepositoryBuilds()
	
	def credential = repoBuilds.getCredentialByID(appServer.credential_id)
	def remoteServer = "${server.name}.${server.domain}"
	def remotePort = appServer.port
	def remoteNode = appServer.nodename
	def remoteServiceName = appServer.servicename
	def remoteUser = credential.username
	def remotePassword = credential.password
	def remoteWebSphereServer = "server1"
	def startAppScript = "${wasScriptsDir}/StartApplication.py"

	println("Starting ${appname} on ${remoteServer}")
	def additionalParams = "${appname} ${remoteNode} ${remoteWebSphereServer} \"${workspace}\""
	
	def globalConfig=PipelineUtils.getJenkinsConfiguration(env.COMPUTERNAME)
	
	wsadminScript(globalConfig.WAS_THIN_CLIENT, startAppScript, remoteUser, remotePassword, remoteServer, remotePort, additionalParams)
	eventsStore(msg:"Application ${appname} started on server ${server.name}:${appServer.name}",type:'APP_STARTED')
}
