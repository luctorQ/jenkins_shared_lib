import com.hastingsdirect.templates.Template

def call(Map params=[template:null,
			subject:'No subject',
			recipients:'', //comma separated list of email addresses
			attachments:'',
			bindings:[:]
		]) {

	try {
		def body=Template.evaluate(params.template,params.bindings)
		println 'body:'+body
/*		params.bindings=null
		emailext(
				to: params.recipients,
				replyTo: 'luchtort@gmail.com',
				subject: params.subject,
				attachmentsPattern: params.attachments,
				body: body
				)*/
		eventsStore(
			msg:"Email ${params.subject} sent to ${params.recipients}",
			type:'EMAIL_SENT',
			ref:[body:body]
			)
	}catch(e) {
		throw new hudson.AbortException('sendEmail error:'+e)
	}
}
