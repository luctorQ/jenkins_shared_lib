table{
	thead{
		tr{
			th(class:'caption',colspan:8,'CREATED APPS')
		}
		tr{
			th(colspan:5,'Builds Info')
			th(colspan:3,'Tests Info')
		}
		tr{
			th('App')
			th('Rev')
			th('Build')
			th('Status')
			th('Branch')
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
				td(b.build.trunk?'TRUNK':(b.build.svnpath =~ /Hastings\/branches\/(\w+)/)[1])
				td(b.junittests.totalCount)
				td(b.junittests.failCount)
				td(b.junittests.skipCount)				
			}
		}
	}
}