package com.hastingsdirect.templates

import groovy.text.markup.MarkupTemplateEngine
import groovy.text.markup.TemplateConfiguration

class Template implements Serializable {
	MarkupTemplateEngine engine
	String template
	Template(String template){
		this.template=template
		TemplateConfiguration config = new TemplateConfiguration();
		engine = new MarkupTemplateEngine(this.getClass().getClassLoader(),config);
	}

	String evaluate(Map bindings=[:]) {
		def compiledTemplate =engine.createTemplateByPath(template).make(bindings)
		return compiledTemplate.toString()
	}
}
