import com.hastingsdirect.templates.Template

import groovy.text.markup.MarkupTemplateEngine
import groovy.text.markup.TemplateConfiguration


def call(){
	def binding = [firstname: 'Jochen', lastname: 'Theodorou', nickname: 'blackdrag']
	println 'nothing'
	def tpl = libraryResource 'com/hastingsdirect/templates/t.tpl'
	
	tpl=tpl.trim().replaceFirst("^([\\W]+)<","<");
	println 'tplclass:'+tpl.getClass()
	println 'tpl:'+tpl
	
	TemplateConfiguration config = new TemplateConfiguration();
	MarkupTemplateEngine engine = new MarkupTemplateEngine(config);
		
	def template=engine.createTemplate(tpl).make(binding)
	
	/*println 'template:'+tpl
	println 'template class:'+tpl.getClass()
	Template template=new Template()*/
//	def tpl=template.evalTemplate(tpl)
	
	println('evaluated:'+template.toString())
	
}