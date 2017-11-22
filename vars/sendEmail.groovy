import com.hastingsdirect.templates.Template

def call(){
	def binding = [firstname: 'Jochen', lastname: 'Theodorou', nickname: 'blackdrag']
	println 'nothing'
	def tpl = libraryResource 'com/hastingsdirect/templates/t.tpl'
	
	tpl=tpl.trim().replaceFirst("^([\\W]+)<","<");
	println 'tplclass:'+tpl.getClass()
	println 'tpl:'+tpl
	
	def engine = new groovy.text.XmlTemplateEngine()
	def template=engine.createTemplate(tpl).make(binding)
	
	/*println 'template:'+tpl
	println 'template class:'+tpl.getClass()
	Template template=new Template()*/
//	def tpl=template.evalTemplate(tpl)
	
	println('evaluated:'+template.toString())
	
}