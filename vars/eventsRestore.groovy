def call(build) {
	def j1EnvVariables = build.buildVariables;
	println 'ext env vairalbles:'+j1EnvVariables
	def extHistory=j1EnvVariables.EVENTS_HISTORY
	return extHistory
}