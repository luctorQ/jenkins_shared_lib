import com.hastingsdirect.templates.Template

import groovy.text.markup.MarkupTemplateEngine
import groovy.text.markup.TemplateConfiguration
import com.hastingsdirect.ep.ExtendedProperty

HISTORY_EVENTS_JSON="""
[{
		"date":1511369584479,"msg":"Build 621 of pc completed with result UNSTABLE","ref":{
			"build":{
				"appname":"pc","artifactoryurl":"","buildidentifier":"26294","buildresult":"UNSTABLE","created_at":1511349799117,"createdate":1511349799000,"deleted":false,"description":"","id":1870,"important":false,"jenkinsbuildnumber":621,"jenkinsbuildurl":"http://bx-cinappd03.network.uk.ad:8080/job/PC%20Build/621/","storedinartifactory":false,"svnpath":"Hastings/branches/CAD7/PolicyCenter/modules/configuration","svnrevisionnumber":26294,"trunk":false,"updated_at":1511349799117
			},"junittests":{
				"failCount":4,"failureDiffString":" / -10","skipCount":0,"testsUrl":"http://bx-cinappd03.network.uk.ad:8080/job/PC%20Build/621//testReport","totalCount":195
			}
		},"type":"APP_BUILD_DONE"
	}]
"""

def call(String templatepath) {
	println 'workspace:'+WORKSPACE
	println 'templatepath:'+templatepath
	println 'pwd:'+pwd()
	def mytemplate=new Template('gogo')
	def HISTORY_EVENTS=ExtendedProperty.fromJson(HISTORY_EVENTS_JSON)

	def bindings=[
		APP_BUILD_DONE:HISTORY_EVENTS.findAll({it.type=='APP_BUILD_DONE'})
	]


	def str=mytemplate.eval('templates/testwithlayout.groovy',bindings)

	return str
}

def call(){
	def binding = [firstname: 'Jochen', lastname: 'Theodorou', nickname: 'blackdrag']
	println 'nothing'
	def tpl = libraryResource 'com/hastingsdirect/templates/Test3.groovy'

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
	return template.toString()

}