import org.boon.Boon
import org.boon.json.JsonFactory
import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper

def call(RunWrapper build) {
	def j1EnvVariables = build.buildVariables;
	def extHistory=j1EnvVariables.EVENTS_HISTORY
	def value=Boon.fromJson(extHistory)
	return value
}

def call() {
	def extHistory=env.EVENTS_HISTORY
	def value=Boon.fromJson(extHistory)
	return value
}