package com.hastingsdirect.ep
import org.boon.Boon
import org.boon.json.JsonFactory;

import com.cwctravel.hudson.plugins.extended_choice_parameter.ExtendedChoiceParameterDefinition
import java.io.Serializable

abstract class ExtendedProperty implements Serializable {

	String name;
	String description;
	String sharedlibrarygroovyclasspath

	ExtendedProperty(String name,String description='',String sharedlibrarygroovyclasspath="c:/tmp/libs_jenkins_global/jenkins_shared_lib.jar"){
		this.name=name
		this.description=description
		this.sharedlibrarygroovyclasspath=sharedlibrarygroovyclasspath
	}

	abstract String groovyScript()

	ExtendedChoiceParameterDefinition createJsonField() {
		return ExtendedProperty.createJsonField(this.name,this.groovyScript(),this.description,this.sharedlibrarygroovyclasspath)
	}

	String groovyScriptFromCodeSource(String groovyFileName) {
		def is=new InputStreamReader(this.getClass().getResourceAsStream(groovyFileName))
		def gsc=new GroovyCodeSource(is,'script_name_dynamic','UTF-8')
		return gsc.scriptText
	}


	/**
	 * method where result may be parsed
	 * @param paramValue Should be params.<name of ExtendedProperty>
	 * @return
	 */
	static Object getValue(paramValue) {
		return paramValue
	}

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

	static Map fromJson(String jsonString) {
		return Boon.fromJson(jsonString)
//		def mapper=JsonFactory.create()
//		return mapper.fromJson(jsonString,Map.class)
	}
	static List fromJsonList(String jsonString) {
		def parsed=Boon.fromJson(jsonString)
		def list=[]
		parsed.each({
			list<<it
		})
		return list
	}


	static toJson(value) {
		return JsonFactory.toJson(value)
	}
}