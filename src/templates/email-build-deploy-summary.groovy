layout 'templates/layouts/layout-email.groovy',
title: 'email',
bodyContents: contents {

	p("Build BRANCH:${PARAMS.SVN_BRANCH}")
	p("Job URL:${JOB.absoluteUrl}")

	p("APPS covered by this build: ${PARAMS.findAll({key,value->key.startsWith('INCLUDE_') && value}).collect({it.key})}")
	p("APPS disabled in CI build: ${PARAMS.findAll({key,value->key.startsWith('INCLUDE_') && !value}).collect({it.key})}")
	
	APP_BUILD_DONE?
			includeGroovy('templates/parts/created-apps.groovy')
			:p('No new applications built this time')

	br()
	
	CURRENTLY_DEPLOYED?
			includeGroovy('templates/parts/deployed-apps.groovy')
			:p('No information about previous deployment')
	p('rest of email')
}