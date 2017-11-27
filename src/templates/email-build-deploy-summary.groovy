layout 'templates/layouts/layout-email.groovy',
title: 'email',
bodyContents: contents {

	p("Build BRANCH:${PARAMS.SVN_BRANCH}")
	p("Job URL: ${JOB.absoluteUrl}")
	
	span("APPS covered by build:")
	COVERED_APPS.each({
		span(it+' ')
	})
	
	br()
	
	span("APPS disabled in build:")
	NOT_COVERED_APPS.each({
		span(it+' ')
	})
	
	br()
	br()
	
	APP_BUILD_DONE?
			includeGroovy('templates/parts/created-apps.groovy')
			:p('No new applications built this time')

	br()
	
	ARTIFACTORY_UPLOAD?
		includeGroovy('templates/parts/artifactory-uploaded.groovy')
		:p('')
	
	br()
		
	CURRENTLY_DEPLOYED?
			includeGroovy('templates/parts/deployed-apps.groovy')
			:p('No information about previous deployment')
	p('rest of email')
}