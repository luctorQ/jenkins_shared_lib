package com.hastingsdirect.templates

import groovy.text.markup.MarkupTemplateEngine
import groovy.text.markup.TemplateConfiguration

class Template implements Serializable {
	
	static String evaluate(String templatepath,Map bindings=[:]) {
		TemplateConfiguration config = new TemplateConfiguration();
		MarkupTemplateEngine engine = new MarkupTemplateEngine(Template.class.getClassLoader(),config);
		
		def compiledTemplate =engine.createTemplateByPath(templatepath).make(bindings)
		return compiledTemplate.toString()
	}
}
