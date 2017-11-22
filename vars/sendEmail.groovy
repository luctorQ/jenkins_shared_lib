import com.hastingsdirect.templates.Template

def call(){
	println 'nothing'
	def tpl = libraryResource 'com/hastingsdirect/templates/Test3.groovy'	
	println 'template:'+tpl
	println 'template class:'+tpl.getClass()
	Template template=new Template()
	def res=template.evalTemplate(tpl)
	
	println('evaluated:'+res)
	
}