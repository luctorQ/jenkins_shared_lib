package com.hastingsdirect.ep
import groovy.lang.GroovyCodeSource;

class PromotedBuildsExt extends ExtendedProperty{

	PromotedBuildsExt(String name,String description=''){
		super(name,description)
	}
	
	@Override
	String groovyScript() {
		return groovyScriptFromCodeSource("scripts/PromotedBuildsExtScript.groovy");
	}
	
	@Override
	static Object getValue(paramValue) {
		def jsonValue=ExtendedProperty.fromJson(paramValue)
		def allSelected=jsonValue.findAll{it.select}
		if(allSelected.size()!=1) {			
			throw new IllegalArgumentException("One build must be selected but selected ${allSelected.size()}")
		}
		
		return [:]<<allSelected[0]
	}
	
}
