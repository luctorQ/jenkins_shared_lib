layout 'templates/layouts/layout-email.groovy',
title: 'email',
bodyContents: contents {

	TEST?p('TEST exists'):p('no TEST')

	//	APP_BUILD_DONE?h3('CREATED APPS'):p('No new crated builds')

	APP_BUILD_DONE?div{
		h3('CREATED APPS')
		table{
			caption('CREATED APPS')
			thead{
				tr{
					th(colspan:4,'Builds Info')
					th(colspan:3,'Tests Info')
				}
				tr{
					th('App')
					th('Rev')
					th('Build')
					th('Status')
					th('Total')
					th('Failed')
					th('Skipped')
				}

			}
			tbody{
				APP_BUILD_DONE.each{b->
					tr{
						td(b.build.appname)
						td(b.build.svnrevisionnumber)
						td{
							a(href:b.build.jenkinsbuildurl,b.build.jenkinsbuildnumber)
						}
						td(b.build.buildresult)
						td(b.junittests.totalCount)
						td(b.junittests.failCount)
						td(b.junittests.skipCount)
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