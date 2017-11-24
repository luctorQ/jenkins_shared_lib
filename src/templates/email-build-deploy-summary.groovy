layout 'templates/layouts/layout-email.groovy',
title: 'email',
bodyContents: contents {

	APP_BUILD_DONE?
			includeGroovy('templates/parts/created-apps.groovy')
			:p('No new applications')

	p('rest of email')
}