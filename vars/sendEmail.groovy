import com.hastingsdirect.templates.Template

def call(){
	def template = libraryResource 'com/hastingsdirect/templates/Test3.groovy'
	
	println 'template:'+template
	template=null;
//	Template template=new Template()
	
	
}