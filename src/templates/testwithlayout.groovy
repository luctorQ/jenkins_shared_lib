layout 'templates/layouts/layout-email.groovy',
title: 'email',
bodyContents: contents {

	TEST?p('TEST exists'):p('no TEST')

	//	APP_BUILD_DONE?h3('CREATED APPS'):p('No new crated builds')

	APP_BUILD_DONE?div{
		h3('CREATED APPS')
		table{
			tr{
				th(colspan:4,'CREATED APPS')
			}
			tr{
				th('Status')
				th('App')
				th('Build No.')
				th('Build url')
			}
			APP_BUILD_DONE.each{b->
				tr{
					td(b.build.buildresult)
					td(b.build.appname)
					td(b.build.jenkinsbuildnumber)
					td{
						a(href:b.build.jenkinsbuildurl,b.build.jenkinsbuildurl)
					}
				}
				tr{
					th(colspan:4,'JUnit TESTS RESULTS')	
				}
				tr{
					th('Total count')
					th('Failed count')
					th('Skip count')
					th('Tests url')
				}
				tr{
					td(b.junittests.totalCount)
					td(b.junittests.failCount)
					td(b.junittests.skipCount)
					td{
						a(href:b.junittests.testsUrl,b.junittests.testsUrl)
					}
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