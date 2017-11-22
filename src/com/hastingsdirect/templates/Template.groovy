package com.hastingsdirect.templates

import groovy.text.markup.MarkupTemplateEngine
import groovy.text.markup.TemplateConfiguration

class Template implements Serializable {
	MarkupTemplateEngine engine
	
	Template(){
		TemplateConfiguration config = new TemplateConfiguration();
		engine = new MarkupTemplateEngine(this.getClass().getClassLoader(),config);
	}
	
	String eval(String template,Map bindings=[:]) {
		def compiledTemplate =engine.createTemplate(template).make(bindings)
		return compiledTemplate.toString()
	}
	
	String templateScriptFromCodeSource(String templateFileName) {
		def is=new InputStreamReader(this.getClass().getResourceAsStream(templateFileName))
		def gsc=new GroovyCodeSource(is,'script_name_dynamic','UTF-8')
		
		def shell=new GroovyShell()
		
		return shell.evaluate(gsc)
	}
}
