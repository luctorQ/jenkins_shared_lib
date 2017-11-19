import org.boon.Boon
import org.boon.json.JsonFactory;

def call(build) {
	def j1EnvVariables = build.buildVariables;
	println 'ext env vairalbles:'+j1EnvVariables
	def extHistory=j1EnvVariables.EVENTS_HISTORY
	def value=Boon.fromJson(jsonString)
	return value
}