import com.hastingsdirect.templates.Template

import groovy.text.markup.MarkupTemplateEngine
import groovy.text.markup.TemplateConfiguration


def call(Map params=[template:null,
			subject:'No subject',
			recipients:'', //comma separated list of email addresses
			attachments:'',
			bindings:[:]
		]) {

	def template=new Template(params.template)
	try {
		def body=template.eval(params.bindings)
		emailext(
				to: params.recipients,
				replyTo: 'luchtort@gmail.com',
				subject: params.subject,
				attachmentsPattern: params.attachments,
				body: body
				)
		eventsStore(msg:"Email ${params.subject} sent to ${params.recipients}",type:'EMAIL_SENT',ref:[body:body,bindings:bindings])
	}catch(e) {
		throw new hudson.AbortException('sendEmail error:'+e)
	}
}
