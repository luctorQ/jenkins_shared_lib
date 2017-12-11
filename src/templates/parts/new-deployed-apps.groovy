table{
	thead{
		tr{
			th(class:'caption',colspan:4,'Deployed applications')
		}
		tr{
			th('App')
			th('Rev')
			th('Server')
			th('Appserver')
		}
	}
	tbody{
		
		DEPLOYED_APP.each{deployedApp->
			tr{
				td(deployedApp.appname)
				td(deployedApp.svnrevisionnumber)
				td(deployedApp.servername)
				td(deployedApp.appservername)
			}
		}
	}
}