import java.util.Map

import org.boon.Boon
import org.boon.json.JsonFactory
//import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper


/*Map rowAsMap(Map row) {
	def rowMap = [:]
	row.keySet().each {column ->
		rowMap[column] = row[column]
	}
	return rowMap
}
*/
def parse(List history=[]) {
	def parsedHistory=[]
	history.each({row
		def rowMap=[:]
		row.keySet().each{key->
			def value=row[key]
			if(key=='date') {
				value=new Date(row[key])
			}
			rowMap[key]=value
		}
		parsedHistory<<rowMap
	})
	return parsedHistory
}

def call(build) {
	def j1EnvVariables = build.buildVariables;
	def extHistory=j1EnvVariables.EVENTS_HISTORY?:'[]'
	def value=Boon.fromJson(extHistory)
	value=parse(value)
	return value
}

def call() {
	def extHistory=env.EVENTS_HISTORY?:'[]'
	def value=Boon.fromJson(extHistory)
	value=parse(value)
	return value
}