package com.hastingsdirect.ep
import groovy.lang.GroovyCodeSource;

class PromotedBuildsExt extends ExtendedProperty{

	String groovyScript() {
		def is=new InputStreamReader(this.getClass().getResourceAsStream("PromotedBuildsExtScript.groovy"))
		def gsc=new GroovyCodeSource(is,'scriptgr','UTF-8')
		return gsc;
	}
	
}
