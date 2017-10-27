import com.hastingsdirect.ep.ExtendedProperty

def call(Map map=[name:'JSON_PARAM',groovyScript:'',description:'']) {
	return ExtendedProperty.createJsonField(map.name,map.groovyScript,map.description)
}

