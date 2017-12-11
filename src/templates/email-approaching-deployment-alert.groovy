layout 'templates/layouts/layout-email.groovy',
title: 'email',
bodyContents: contents {


	p("Hi guys we have detected that newer artifacts are present for branch ${PARAMS.SVN_BRANCH}")

	p({
		span("This is automatically generated email from job:")
		a(href:JOB.absoluteUrl,JOB.jobName+'-'+JOB.displayName)
	})

	APP_BUILD_DONE?
			includeGroovy('templates/parts/created-apps.groovy')
			:p({
				span('No new applications built this time but latest revisions ready to deploy are: ')
				LATEST_SVN_REVISIONS.keySet().each({appname->
					span(appname+':'+LATEST_SVN_REVISIONS[appname]+' ')
				})
			})

	p("You can expect shortly (in ${(PARAMS.DEPLOYMENT_QUIET_PERIOD as Integer)/60} min) new deployment begins on environment ${PARAMS.ENVIRONMENT}")

	p("If you want to stop new deployment please reply to this email we will try abort if it is not too late :/")

	p("As a reminder now this is configured as below")

	p({
		CURRENTLY_DEPLOYED?
				includeGroovy('templates/parts/initially-deployed-apps.groovy')
				:p('No information about previous deployment')
	})
}