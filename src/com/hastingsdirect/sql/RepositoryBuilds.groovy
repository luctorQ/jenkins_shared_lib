package com.hastingsdirect.sql

import java.util.ArrayList

import groovy.sql.Sql

class RepositoryBuilds extends Repository{

	public List buildsOnePromoted() {
		Sql sql=CMDBConnection.createConnection();
		List promoted=[]
		def rows=sql.rows('select * from onebuildpromotion order by "cijenkinsbuildid" desc')
		rows.each({row->
			promoted<<rowAsMap(row)
		})
		sql.close()
		return promoted;
	}
	
	public List getGlobalConfigByServerName(server) {
		Sql sql =CMDBConnection.createConnection();
		def query = "select name, value from globalconfiguration where server = '${server}'"
		def rows = sql.rows(query)
		List config=[]
		rows.each({row->
			config<<rowAsMap(row)
		})
		sql.close()
		return config
	}
	
	public updateOneBuildPromotion(Number cijobid,String branch,Map builds,String jobstatus,String smoketeststatus) {
		println 'updateOneBuildPromotion builds:'+builds
		Sql sql =CMDBConnection.createConnection();
		
		def insert = "insert into onebuildpromotion ( cijenkinsbuildid,branch,absvnrevisionnumber,ccsvnrevisionnumber,pcsvnrevisionnumber,bcsvnrevisionnumber,abjenkinsbuildid,ccjenkinsbuildid,pcjenkinsbuildid,bcjenkinsbuildid,jobstatus,smoketeststatus)"
		insert += "values (${cijobid}, '${branch}', ${builds.ab?.svnrevisionnumber}, ${builds.cc?.svnrevisionnumber}, ${builds.pc?.svnrevisionnumber}, ${builds.bc?.svnrevisionnumber}, ${builds.ab?.jenkinsbuildnumber}, ${builds.cc?.jenkinsbuildnumber}, ${builds.pc?.jenkinsbuildnumber}, ${builds.bc?.jenkinsbuildnumber}, '${jobstatus}', '${smoketeststatus}')"
	
		println 'updateOneBuildPromotion insert sql:'+insert
		
		def rows = sql.executeInsert(insert)
		sql.close()
		
		return (rows != [])
	}
		
	public Map getBuildByAppAndSvnRev(String appname,Number svnrevision) {
		Sql sql =CMDBConnection.createConnection();
		def query = "select * from builds where appname='${appname}' and svnrevisionnumber=${svnrevision}"
		def result=rowAsMap(sql.rows(query)[0])
		sql.close()
		return result
	}
	
	public Map getBuildByAppnameJenkinsBuildId(String appname,Number buildId) {
		Sql sql =CMDBConnection.createConnection();
        def query = "select * from builds where appname='${appname}' and jenkinsbuildnumber=${buildId}"
		def result=rowAsMap(sql.rows(query)[0])
		sql.close()
		return result
    }
	
	public Map getAppOnServer(String appname,Number appserverId) {
		Sql sql =CMDBConnection.createConnection();
		def query = "select * from apps where name='${appname}' and appserver_id=${appserverId}"
		def result=rowAsMap(sql.rows(query)[0])
		sql.close()
		return result
	}
	
	public List getAllAppsOnAppserver(appserverId) {
		Sql sql =CMDBConnection.createConnection();
		def query = "select * from apps where appserver_id=${appserverId}"
		List apps=[]
		def rows = sql.rows(query)
		rows.each({row->
			apps<<rowAsMap(row)
		})
		sql.close()
		return apps
	}

	
	public Map getAppserverCredentialsByID(Number credentialsId) {
		Sql sql =CMDBConnection.createConnection();
		def query = "select * from credentials where id=${credentialsId}"
		def result=rowAsMap(sql.rows(query)[0])
		sql.close()
		return result
	}
	
	
	public List getDeployedAppsOnEnvironmentDetails(environmentId){
		Sql sql =CMDBConnection.createConnection();
		List apps=[]
		def query = "select b.*,a.id as app_id,a.* from builds as b join apps as a on b.id = a.build_id where a.environment_id=${environmentId}"
		def rows = sql.rows(query)
		rows.each({row->
			apps<<rowAsMap(row)
		})

		sql.close()
		return apps;
	}
	
	public Long getEnvironmentIdByName(String name) {
		Sql sql =CMDBConnection.createConnection();
		def query = "select id from environments where name=${name}"
		def result=sql.rows(query)[0]
		sql.close()
		return result.id;
	}
	
	public Map getAppserverByID(Number appServerId){
		Sql sql =CMDBConnection.createConnection();
		def query = "select * from appservers where id=${appServerId}"
		def result=rowAsMap(sql.rows(query)[0])
		sql.close()
		return result
	}
	
	public Map getServerByID(Number serverId) {
		Sql sql =CMDBConnection.createConnection();
		def query = "select * from servers where id=${serverId}"
		def result=rowAsMap(sql.rows(query)[0])
		sql.close()
		return result
	}
	
	public Map getCredentialByID(appServerCredentialId) {
		Sql sql =CMDBConnection.createConnection();
		def query = "select * from credentials where id=${appServerCredentialId}"
		def result=rowAsMap(sql.rows(query)[0])
		sql.close()
		return result
	}	
	
	public String updateAppWithDeploymentInfo(buildID, deploymentDate, buildUser, deploymentURL, appID) {
		Sql sql =CMDBConnection.createConnection();
		def update = ""
		update += "update Apps set "
		update += "build_id=${buildID}, "
		update += "updated_at=sysdatetime()"
		update += "where id=${appID}"
		try{
			sql.execute(update)
		} catch (Exception e) {
			return "Exception thrown: [${e}]"
		}
		
		if(buildUser==null || buildUser=='null') {
			buildUser = 'Continuous Integration'
		}
		
		//Add an App Deployment history record.
		def insert = ""
		insert += "insert into appdeploymenthistories (app_id, build_id, deploymentdate, deploymentuser, deploymenturl, created_at, updated_at) "
		insert += "values (${appID}, ${buildID}, '${deploymentDate}', '${buildUser}', '${deploymentURL}', sysdatetime(), sysdatetime()) "
		try{
			sql.execute(insert)
		} catch (Exception e) {
			return "Exception thrown: [${e}]"
		}
		sql.close()

		return "Successful update"
	}
	
	public updateBuildInfoArtifactoryDeployment(buildId,artifactoryUrl) {
		Sql sql =CMDBConnection.createConnection();
		def update = "update builds set artifactoryurl='${artifactoryUrl}', storedinartifactory='True' where id=${buildId}"
		println "update query:"+update
		try{
			sql.execute(update)
		} catch (Exception e) {
			return "Exception thrown: [${e}]"
		}
		sql.close()
	}
	
}
