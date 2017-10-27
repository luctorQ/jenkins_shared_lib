import com.hastingsdirect.ep.ExtendedProperties

def call(String name,String description,String groovyScript) {
	return ExtendedProperties.createJsonField(name,groovyScript,description)
}

