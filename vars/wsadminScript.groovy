def call(wsadmin, wasScript, user, password, server, port, additionalParams) {
	def wasParams = "-lang jython -conntype SOAP"
	def userPass = "-user ${user} -password ${password}"
	def remoteHost = "-host ${server} -port ${port}"
	bat "call ${wsadmin} ${wasParams} ${remoteHost} ${userPass} -f \"${wasScript}\" ${additionalParams}"
}