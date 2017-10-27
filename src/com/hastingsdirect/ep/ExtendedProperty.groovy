package com.hastingsdirect.ep

import com.cwctravel.hudson.plugins.extended_choice_parameter.ExtendedChoiceParameterDefinition
import java.io.Serializable

abstract class ExtendedProperty implements Serializable {


	static ExtendedChoiceParameterDefinition createJsonField(
			String name,
			String groovyscript='',
			String description='',
			sharedlibrarygroovyclasspath="c:/tmp/libs_jenkins_global/jenkins_shared_lib.jar") {

		ExtendedChoiceParameterDefinition parameterDefinition= new ExtendedChoiceParameterDefinition(
				name, //name
				"PT_JSON",//type
				null,//value
				null,//project name
				null,//property file
				groovyscript,//groovy script
				null,
				null,// bindings
				sharedlibrarygroovyclasspath,//groovyclasspath
				null, // propertykey
				null, //default value
				null,
				null,
				null,
				null, //default bindings
				null,
				null,
				null, //descriptionPropertyValue
				null,
				null,
				null,
				null,
				null,
				null,
				null,// javascript file
				null, // javascript
				false, // save json param to file
				false, // quote
				2, // visible item count
				description, //description
				","
				)
		return parameterDefinition;
	}
	
	
	String groovyScriptFromCodeSource(String groovyFileName) {
		def is=new InputStreamReader(this.getClass().getResourceAsStream(groovyFileName))
		def gsc=new GroovyCodeSource(is,'scriptgr','UTF-8')
		return gsc.scriptText
	}
}