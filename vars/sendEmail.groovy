import com.hastingsdirect.templates.Template

def call(Map params=[template:null,
			subject:'No subject',
			recipients:'', //comma separated list of email addresses
			attachments:'',
			bindings:null
		]) {

//	try {
		def body=Template.evaluate(params.template,params.bindings)
		println 'body:'+body
//		params.bindings=null
		
		eventsStore(
			msg:"Email ${params.subject} sent to ${params.recipients}",
			type:'EMAIL_SENT',
			ref:[body:body]
			)

		emailext(
				to: params.recipients,
				replyTo: 'luchtort@gmail.com',
				subject: params.subject,
				attachmentsPattern: params.attachments,
				body: body
				)
/*	}catch(e) {
		e.printStackTrace()
		throw new hudson.AbortException('sendEmail error:'+e)
	}
*/}
