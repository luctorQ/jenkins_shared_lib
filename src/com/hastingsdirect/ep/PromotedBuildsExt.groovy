package com.hastingsdirect.ep
import groovy.lang.GroovyCodeSource;

class PromotedBuildsExt extends ExtendedProperty{

	String groovyScript() {
		def gsc=new GroovyCodeSource(new BufferedReader(this.getClass().getResourceAsStream("PromotedBuildsExtScript.groovy")))
		return gsc;
	}
	
}
