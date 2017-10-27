import com.hastingsdirect.ep.ExtendedProperties

def call(Map map=[:]) {
	def defaultMap = [name:'JSON_PARAM',groovyScript:'',description:'']
	defaultMap<<map
	return ExtendedProperties.createJsonField(defaultMap.name,defaultMap.groovyScript,defaultMap.description)
}

