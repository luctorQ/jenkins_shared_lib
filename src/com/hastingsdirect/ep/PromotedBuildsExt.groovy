package com.hastingsdirect.ep
import groovy.lang.GroovyCodeSource;

class PromotedBuildsExt extends ExtendedProperty{

	PromotedBuildsExt(String name,String description='',String sharedlibrarygroovyclasspath="c:/tmp/libs_jenkins_global/jenkins_shared_lib.jar"){
		super(name,description,sharedlibrarygroovyclasspath)
	}
	
	@Override
	String groovyScript() {
		return groovyScriptFromCodeSource("scripts/PromotedBuildsExtScript.groovy");
	}

	
}
