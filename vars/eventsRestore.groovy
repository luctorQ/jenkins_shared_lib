import java.util.Map

import com.hastingsdirect.pipeline.PipelineUtils
import org.boon.Boon
import org.boon.json.JsonFactory
import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper


def parse(String jsonHistory) {
//	def history=Boon.fromJson(jsonHistory?:'[]')
	def history=readJSON(text:jsonHistory?:'[]')
	def parsedHistory=[]
	history.each({row->
		def rowMap=[:]
		row.keySet().each{key->
			def value=row[key]
			if(key=='date') {
				value=new Date(row[key])
			}
/*			if(key=='ref') {
				value=PipelineUtils.convertJsonToSerialized(value)
			}
*/			rowMap[key]=value
		}
		parsedHistory<<rowMap
	})
	return parsedHistory
}

def call(RunWrapper build) {
	def j1EnvVariables = build.buildVariables;
	def extHistory=j1EnvVariables.EVENTS_HISTORY
	def value=parse(extHistory)
	return value
}

def call() {
	def extHistory=env.EVENTS_HISTORY
	def value=parse(extHistory)
	return value
}