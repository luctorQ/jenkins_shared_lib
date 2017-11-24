package com.hastingsdirect.templates

import groovy.text.markup.MarkupTemplateEngine
import groovy.text.markup.TemplateConfiguration

class Template implements Serializable {
	
	static String evaluate(String template,Map bindings=[:]) {
		TemplateConfiguration config = new TemplateConfiguration();
		MarkupTemplateEngine engine = new MarkupTemplateEngine(Template.class.getClassLoader(),config);
		
		def compiledTemplate =engine.createTemplateByPath(template).make(bindings)
		return compiledTemplate.toString()
	}
}
