table{
	thead{
		tr{
			th(class:'caption',colspan:3,'Previously deployed apps')
		}
		tr{
			th('App')
			th('Rev')
			th('Category')
			th('Build')
		}
	}
	tbody{
		def CD=CURRENTLY_DEPLOYED[0]?CURRENTLY_DEPLOYED[0]:[]
		CD.each{build->
			tr{
				td(build.appname)
				td(build.svnrevisionnumber)
				td(build.category)
				td{
					a(href:build.jenkinsbuildurl,build.jenkinsbuildnumber)
				}
			}
		}
	}
}