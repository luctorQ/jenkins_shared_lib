table{
	thead{
		tr{
			th(class:'caption',colspan:7,'CREATED APPS')
		}
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