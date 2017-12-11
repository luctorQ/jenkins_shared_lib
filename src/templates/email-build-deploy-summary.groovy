layout 'templates/layouts/layout-email.groovy',
title: 'email',
bodyContents: contents {

	ONE_BUILD_PROMOTION_ID?
	p("THIS BUILD IS PROMOTED. You can refer to no ${ONE_BUILD_PROMOTION_ID} with further deployments requests on different environements")
	:p("This build is not promoted")

	p("Build BRANCH:${PARAMS.SVN_BRANCH}")
	p("Job URL: ${JOB.absoluteUrl}")

	span("APPS covered by build:")
	COVERED_APPS.each({ span(it+' ') })

	br()

	span("APPS disabled in build:")
	NOT_COVERED_APPS.each({ span(it+' ') })

	br()
	br()

	p({
		span("Smoke test result:")
		span(SMOKE_TESTED?.result)
		br()
		span('Please refer to attached smoke test report')
	})



	APP_BUILD_DONE?
			includeGroovy('templates/parts/created-apps.groovy')
			:p('No new applications built this time')

	br()

	DEPLOYED_APP?
			includeGroovy('templates/parts/new-deployed-apps.groovy')
			:p('No applications deployed this time')

	ARTIFACTORY_UPLOAD?
			includeGroovy('templates/parts/artifactory-uploaded.groovy')
			:p('')

	br()

	p({
		CURRENTLY_DEPLOYED?
				includeGroovy('templates/parts/initially-deployed-apps.groovy')
				:p('No information about previous deployment')
	})

	br()

	p({
		HISTORY_EVENTS?
				includeGroovy('templates/parts/deployment-history.groovy')
				:p('No build history')
	})
}