layout 'templates/layouts/layout-email.groovy',
title: 'email',
bodyContents: contents {

	TEST?p('TEST exists'):p('no TEST')

	//	APP_BUILD_DONE?h3('CREATED APPS'):p('No new crated builds')

	APP_BUILD_DONE?div{
		h3('CREATED APPS')
		table{
			APP_BUILD_DONE.each{b->
				row{
					td(b.appname)
					td(b.buildidentifier)
				}
			}

		}
	}:p('No new crated builds')

	/*	APP_BUILD_DONE.each{
	 fragment 
	 }
	 pc: 26186 svn:Hastings/branches/CAD7/PolicyCenter/modules/configuration
	 status:UNSTABLE
	 build job: http://bx-cinappd03.network.uk.ad:8080/job/PC%20Build/609/
	 JUnit Tests TOTAL:195 FAILED:7
	 */	
	p('This is the body')
	p('new body')
}