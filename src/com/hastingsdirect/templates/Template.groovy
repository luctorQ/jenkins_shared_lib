package com.hastingsdirect.vo

class Template implements Serializable {
	
	
	String evalTemplate(template) {
		println 'evalTemplate:'+template;
		def shell=new GroovyShell()
		
	}
	
	String templateScriptFromCodeSource(String templateFileName) {
		def is=new InputStreamReader(this.getClass().getResourceAsStream(templateFileName))
		def gsc=new GroovyCodeSource(is,'script_name_dynamic','UTF-8')
		
		def shell=new GroovyShell()
		
		return shell.evaluate(gsc)
	}
}
