package com.hastingsdirect.ep

import com.cwctravel.hudson.plugins.extended_choice_parameter.ExtendedChoiceParameterDefinition
import com.hastingsdirect.sql.RepositoryBuilds
import java.io.Serializable

class ExtendedProperties implements Serializable {


	static ExtendedChoiceParameterDefinition createJsonField(String name,String groovyscript,String description='') {

		ExtendedChoiceParameterDefinition parameterDefinition= new ExtendedChoiceParameterDefinition(
				name, //name
				"PT_JSON",//type
				null,//value
				null,//project name
				null,//property file
				groovyscript,//groovy script
				null,
				null,// bindings
				"c:/tmp/libs_jenkins_global/jenkins_shared_lib.jar",//groovyclasspath
				//"c:/Users/PLUSZYNSKI/.jenkins/plugins/workflow-cps-global-lib/WEB-INF/lib/workflow-cps-global-lib.jar",
				//	"c:/Users/PLUSZYNSKI/.jenkins/jobs/pipeline_test_libs/workspace@libs/test-lib/src",//groovyclasspath
				null, // propertykey
				null,//"VALUE, B", //default value
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

	public ExtendedProperties() {
		//		def builds=new RepositoryBuilds()
		//		builds.buildsOnePromoted()
	}

	public String test() {
		return "EP test"
	}

	public String test2() {
		return "test 2"
	}
}