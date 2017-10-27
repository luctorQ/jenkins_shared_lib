package com.hastingsdirect.ep
import groovy.lang.GroovyCodeSource;

class PromotedBuildsExt extends ExtendedProperty{

	
	String groovyScript() {
		return groovyScriptFromCodeSource("scripts/PromotedBuildsExtScript.groovy");
	}
	
}
