import com.hastingsdirect.ep.ExtendedProperties

def call(Map map=[name:'JSON_PARAM',groovyScript:'',description:'']) {
	return ExtendedProperties.createJsonField(map.name,map.groovyScript,map.description)
}

