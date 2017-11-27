package com.hastingsdirect

class Sql implements Serializable {

  def sql
  def url = "jdbc:sqlserver://bx1-prd-sql01.network.uk.ad:1433;databaseName=RMCMDB"
  def user = "JenkinsUser"
  def password = "Ak5TLEIB5HSDSB2otvN0ONdejwLkXtup!"

  def driver = 'com.microsoft.sqlserver.jdbc.SQLServerDriver'

  public void createConnection() {
    this.sql = groovy.sql.Sql.newInstance(this.url, this.user, this.password, this.driver)
  }

  void overrideDefaultSettings(url, user, password) {
    this.url = url 
    this.user = user 
    this.password = password 
  }

  ArrayList queryRows(query) {
    return this.sql.rows(query)
  }

  ArrayList getAppsOnAppserver(id) {
    def query = "select name from apps where appserver_id=${id}"
    return this.sql.rows(query)
  }

  ArrayList getInsurerBAUInfo() {
    def query = ""
    query += " select  e.name as environmentname, "
    query += " s.name as servername, "
    query += " s.domain as serverdomain, "
    query += " asv.appport as appport,"
    query += " asv.port as port,"
    query += " a.name as appname, "
    query += " a.id as app_id, "
    query += " asv.name as appservername, "
    query += " asv.id as appserverid, "
    query += " asv.servicename, "
    query += " asv.profileroot, "
    query += " asv.nodename,"
    query += " c.username as wasuser, "
    query += " c.password as waspassword,"
    query += " a.sso as sso,"
    query += " b.svnrevisionnumber"
    query += " from appservers as asv "
    query += " join servers as s "
    query += " on s.id = asv.server_id "
    query += " join environments as e "
    query += " on e.id = s.environment_id  "
    query += " join apps as a "
    query += " on a.appserver_id = asv.id "
    query += " join builds as b"
    query += " on a.build_id = b.id"
    query += " join credentials as c "
    query += " on c.id = asv.credential_id "
    query += " where asv.appservertype = 'was' "
    query += " and e.retired=0 "
    query += " and e.isproduction = 0 "
    query += " and (e.name like 'INS%' or e.name like 'PROM%') "
    query += " and a.name in ('ab', 'bc', 'pc') "
    query += " order by servername, environmentname, appname "

    return this.sql.rows(query)
  }

  ArrayList getGuidewireServersForEnvironment(environmentname) {
    def query = ''
    query += " select servername + '.' + domain as servername, appname "
    query += " from v_allappurls "
    query += " where environmentname = '${environmentname}' "
    query += " and appname in ('ab','bc','cc','pc') "

    return this.sql.rows(query)
  }

  ArrayList getProductionVersions() {
    def query = ""
    query += " select distinct a.appname, a.build_id, b.svnrevisionnumber, b.artifactoryurl "
    query += " from v_allappurls as a "
    query += " join builds as b "
    query += " on b.id = a.build_id "
    query += " where environmentname = 'PRODUCTION' "
    query += " and a.appname in ('ab', 'bc', 'pc' ) "

    return this.sql.rows(query)
  }

  groovy.sql.GroovyRowResult getAppserverByServerAndProfileName(serverName, profileName) {
    def query = "select appservers.* from servers join appservers on servers.id = appservers.server_id "
    query += "where appservers.name = '${profileName}' and servers.name = '${serverName}'"
    return this.sql.rows(query)[0]
  }

  groovy.sql.GroovyRowResult getAppserverByID(id) {
    def query = "select * from appservers where id=${id}"
    return this.sql.rows(query)[0]
  }

  groovy.sql.GroovyRowResult getDatabaseSettingsById(id) {
    def query = "select * from databasesettings where id=${id}"
    return this.sql.rows(query)[0]
  }

  groovy.sql.GroovyRowResult getServerByID(id) {
    def query = "select * from servers where id=${id}"
    return this.sql.rows(query)[0]
  }

  groovy.sql.GroovyRowResult getEnvironmentByID(id) {
    def query = "select * from environments where id=${id}"
    return this.sql.rows(query)[0]
  }

  groovy.sql.GroovyRowResult getEnvironmentIdByName(name) {
    def query = "select id from environments where name=${name}"
    return this.sql.rows(query)[0]
  }

  groovy.sql.GroovyRowResult getEnvironmentByAppID(id) {
    def query = "select e.* from environments as e join apps as a on a.environment_id= e.id where a.id=${id}"
    return this.sql.rows(query)[0]
  }

  groovy.sql.GroovyRowResult getCredentialByID(id) {
    def query = "select * from credentials where id=${id}"
    return this.sql.rows(query)[0]
  }

  groovy.sql.GroovyRowResult getBuildByID(id) {
    def query = "select * from builds where id=${id}"
    return this.sql.rows(query)[0]
  }

  groovy.sql.GroovyRowResult getArtifactoryBuildBySVNRevNumAndAppName(revNum, appName, storedInArtifactory) {
    def query = "select * from builds where svnrevisionnumber=${revNum} and appname='${appName}' and storedinartifactory=${storedInArtifactory ? 1 : 0}"
    return this.sql.rows(query)[0]
  }

  groovy.sql.GroovyRowResult getAppByAppID(id) {
    def query = "select * from apps where id=${id}"
    return this.sql.rows(query)[0]
  }

  groovy.sql.GroovyRowResult getBuildDeployedToAppID(id) {
    def query = "select b.* from builds as b join apps as a on b.id = a.build_id where a.id = ${id}"
    return this.sql.rows(query)[0]
  }

  groovy.sql.GroovyRowResult getBuildForCI(appName, jenkinsBuildNum, svnRev) {
    def query = "select * from builds where appname='${appName}' and jenkinsbuildnumber=${jenkinsBuildNum} and svnrevisionnumber=${svnRev}"
    return this.sql.rows(query)[0]
  }

  boolean deleteBackup(backup_id) {
    def stmt = "delete from backups where id = ${backup_id}"
    
    try {
      this.sql.execute(stmt)
    }
    catch(e) {
      println "Error deleting backup: ${e}"
      return false
    }

    return true
  }

  groovy.sql.GroovyRowResult getDBDetailsFromAppID(appID) {
    def query = "select  dbs.databasename, "
    query += "dbs.databaseserver, "
    query += "dbs.databaseport, "
    query += "c.username, "
    query += "c.password, "
    query += "ro_c.username as ro_username, "
    query += "ro_c.password as ro_password "
    query += "from databasesettings as dbs "
    query += "join apps as a "
    query += "on a.databasesetting_id = dbs.id "
    query += "left join credentials as c "
    query += "on c.id = dbs.credential_id "
    query += "left join credentials as ro_c "
    query += "on ro_c.id = dbs.readonlycredential_id "
    query += "where a.id = ${appID} "
    return this.sql.rows(query)[0]
  }

// For Jenkins Plugin Extended Parameter Choice
ArrayList getAPIGatewayEnvironmentInfoForEPC(prodOnly) {
  def query = """
  select appservers.name as AppserverName, servers.name as ServerName, environments.name as EnvironmentName, apps.id as AppID from apps
  join environments on apps.environment_id = environments.id
  join appservers on apps.appserver_id = appservers.id
  join servers on appservers.server_id = servers.id
  where apps.name = 'apigw' and environments.isproduction = ${prodOnly ? 1 : 0}
  """

  def rows = this.sql.rows(query)
  return rows
}

ArrayList getIISEnvironmentDetailsWithAppName(apps, prodOnly) {
  def _apps = ""
  for (app in apps.split(",")) {
    _apps += "'${app}'"
    _apps += ","
  }
  _apps = _apps[0..-2]

  def query = "select  e.name as EnvironmentName, "
  query += "s.name as ServerName, "
  query += "s.domain as ServerDomain, "
  query += "asv.name as AppserverName, "
  query += "asv.id as AppserverId, "
  query += "a.name as AppName, "
  query += "a.category as AppCategory, "
  query += "a.id as AppId, "
  query += "a.iissitename as IISSiteName "
  query += "from apps as a "
  query += "join environments as e "
  query += "on e.id = a.environment_id "
  query += "join appservers as asv "
  query += "on asv.id = a.appserver_id "
  query += "join servers as s "
  query += "on asv.server_id = s.id "
  query += "where e.isproduction = ${prodOnly ? 1 : 0} "
  if (!"".equals(_apps)) {
    query += "and a.name in (${_apps}) "
    } else {
      query += "and a.name in ('') "
    }
    query += "order by environmentname, appname "

    def rows = this.sql.rows(query)
    return rows
  }

  ArrayList getAppserversSelectionForExtendedChoice(appservertype, prodOnly) {
    def query = ""
    query += "select i.* from ( "
    query += "select  env.name as EnvironmentName,  "
    query += "srv.name as ServerName,  "
    query += "srv.domain as ServerDomain,  "
    query += "appservers.name as AppserverName,  "
    query += "appservers.id as AppserverID,  "
    query += "stuff((select ', ' + apps.name from apps where apps.appserver_id = appservers.id and apps.switchedoff = 0 for xml path('')), 1, 2, '') as Apps,  "
    query += "(case when exists(select apps.batch from apps where apps.appserver_id = appservers.id and apps.batch = 1) THEN 1 ELSE 0 END) as Batch,  "
    query += "(select top 1 apps.category from apps where apps.appserver_id = appservers.id) as Category  "
    query += "from appservers  "
    query += "join servers as srv  "
    query += "on srv.id = appservers.server_id  "
    query += "join environments as env  "
    query += "on env.id = srv.environment_id "
    query += "where appservers.appservertype = '${appservertype}' "
    query += "and env.retired=0 "
    query += "and env.isproduction = ${prodOnly ? 1 : 0}) i "
    query += "where i.Apps is not null "
    query += "order by i.EnvironmentName, i.Category "

    def rows = this.sql.rows(query)
    return rows
  }

  ArrayList getServersForWindowsUpdate() {
    def query = ''
    query += "select e.name as environmentname, s.name as servername, s.domain "
    query += "from environments as e "
    query += "join servers as s "
    query += "on s.environment_id = e.id "
    query += "where s.datapipe = 0 "
    query += "and e.retired = 0 "
    query += "order by e.name "

    def rows = this.sql.rows(query)
    return rows
  }

// Get environment name, server name and profile.
// Find the above with the APP pararm provided and prod mode defined.
ArrayList getProfilesWithAppSpecified(apps, prodOnly) {
  def _apps = ""
  for (app in apps.split(",")) {
    _apps += "'${app}'"
    _apps += ","
  }
  _apps = _apps[0..-2]

  def query = ""
  query += "select e.name as EnvironmentName, "
  query += "s.name as ServerName, "
  query += "s.domain as ServerDomain, "
  query += "a.category as AppCategory, "
  query += "a.batch as Batch, "
  query += "a.id as AppID, "
  query += "a.name as AppName, "
  query += "asv.name as AppserverName, "
  query += "asv.id as AppserverID "
  query += "from environments as e "
  query += "join servers as s "
  query += "on e.id=s.environment_id "
  query += "join appservers as asv "
  query += "on s.id=asv.server_id "
  query += "join apps as a "
  query += "on a.appserver_id=asv.id "
  query += "where e.isProduction = ${prodOnly ? 1 : 0} "
  if (!"".equals(_apps)) {
    query += "and a.name in (${_apps}) "
    } else {
      query += "and a.name in ('') "
    }
    query += "and a.switchedoff = 0 "
    query += "and e.retired = 0 "
    query += "order by EnvironmentName, AppCategory, AppName "

    def rows = this.sql.rows(query)
    return rows
  }

  ArrayList getProfilesForBackupOrRestore(boolean backup) {
    def query = ""
    query += " select  e.name as environmentname, "
    query += " s.name as servername, "
    query += " s.domain as serverdomain, "
    query += " a.category as appcategory, "
    query += " a.batch as batch, "
    query += " a.id as appid, "
    query += " a.name as appname, "
    query += " asv.name as appservername, "
    query += " asv.id as appserverid,"
    query += " dbs.databaseserver,"
    query += " dbs.databaseport,"
    query += " dbs.databasename,"
    query += " c.username,"
    query += " c.password"
    query += " from environments as e "
    query += " join servers as s "
    query += " on e.id=s.environment_id "
    query += " join appservers as asv "
    query += " on s.id=asv.server_id "
    query += " join apps as a "
    query += " on a.appserver_id=asv.id  "
    query += " join databasesettings as dbs"
    query += " on dbs.id = a.databasesetting_id"
    query += " join credentials as c"
    query += " on c.id = dbs.credential_id"
    query += " where e.isProduction = 0"
    query += " and a.switchedoff = 0 "
    query += " and e.retired = 0 "
    if(backup) {
      query += " and e.canbebackedupfrom = 1"
    }
    else {
      query += " and e.canberestoredto = 1"
    }
    query += " order by environmentname, appcategory, appname"
    def rows = this.sql.rows(query)
    return rows
  }

  ArrayList getBackupSets() {
    def query = ''
    query += " select * "
    query += " from  "
    query += " (select distinct description, environmentname, created_at, "
    query += " ROW_NUMBER() over(partition by description order by created_at desc) as rn "
    query += " from backups) a "
    query += " where rn = 1 "
    query += " order by created_at desc "
    def rows = this.sql.rows(query)
    return rows
  }

  ArrayList getOldBuilds(int retentionDays) {
    String query = ''
    query += " select * "
    query += " from builds "
    query += " where created_at < dateadd(day, -${retentionDays}, SYSDATETIME()) "
    query += " and important = 0 "
    query += " and deleted = 0 "
    query += " and storedinartifactory = 1 "
    query += " and appname in ('ab', 'bc', 'cc', 'pc', 'ec') "

    def rows = this.sql.rows(query)
    return rows
  }

  ArrayList getBackupsFromDescription(description) {
    def query = "select * from backups where description = '${description}'"
    def rows = this.sql.rows(query)
    return rows
  }

  ArrayList getIISAppInfoByEnvironment(app, prodOnly) {
    def query = ""
    query += "select  e.name as EnvironmentName, "
    query += "s.name as ServerName, "
    query += "s.domain as ServerDomain, "
    query += "s.id as ServerID, "
    query += "a.iissitename as SiteName, "
    query += "a.id as AppID, "
    query += "a.name as AppName, "
    query += "asv.name as AppserverName, "
    query += "asv.id as AppserverID "
    query += "from environments as e "
    query += "join servers as s "
    query += "on s.environment_id = e.id "
    query += "join appservers as asv "
    query += "on s.id = asv.server_id "
    query += "join apps as a "
    query += "on a.appserver_id = asv.id "
    query += "where a.name = 'ISL' "
    query += "and e.isproduction = ${prodOnly ? 1 : 0} "
    query += "order by EnvironmentName, SiteName "

    def rows = this.sql.rows(query)
    return rows
  }

  ArrayList getBuildsForApp(app, displayLimit) {
    def query = ""
    query += "select top ${displayLimit} b.* "
    query += "from builds as b "
    query += "where appname = '${app}' "
    query += "and storedinartifactory = 1 "
    query += "order by b.svnrevisionnumber desc, createdate desc "

    def rows = this.sql.rows(query)
    return rows
  }

  String getGlobalConfigItem(configName) {
    def query = "select value from globalconfiguration where name = '${configName}'"
    def result = this.sql.rows(query)
    return result[0]['value']
  }

  String getUrlForAppId(app_id) {
    def query = "select url from v_allappurls where appid = ${app_id}"
    def result = this.sql.rows(query)
    return result[0]['url']
  }

  Integer getNumberOfBuildsForAppName(app) {
    def query = "select count(*) as count from builds where appname = '${app}'"
    def result = this.sql.rows(query)
    return result[0]['count']
  }

//get environment name, server name and profile.
// find the above where there are no apps configured.
ArrayList getProfilesWithNoAppsConfigured() {
  def query = ""
  query += "select  e.name as EnvironmentName, "
  query += "s.name as ServerName, "
  query += "asv.name as AppserverName, "
  query += "asv.id as AppserverID "
  query += "from environments as e "
  query += "join servers as s "
  query += "on s.environment_id = e.id "
  query += "join appservers as asv "
  query += "on asv.server_id = s.id "
  query += "where (select count(*) from apps where appserver_id=asv.id) < 1 "
  query += "or asv.needsreprofiling = 1"

  def rows = this.sql.rows(query)
  return rows
}

ArrayList getGlobalConfigByServerName(server) {
  def query = "select name, value from globalconfiguration where server = '${server}'"

  def rows = this.sql.rows(query)
  return rows
}

ArrayList getActiveRatebooksFromPolicyCenter() {
  def query = "select CreateTime, BookName, BookEdition from pc_ratebook where Status = 4"
  def rows = this.sql.rows(query)
  return rows 
}

ArrayList getNonActiveRatebooksFromPolicyCenter() {
  def query = "select CreateTime, BookName, BookEdition from pc_ratebook where Status != 4 and Retired = 0"
  def rows = this.sql.rows(query)
  return rows 
}

ArrayList getRatebooks() {
  def query = "select * from productionratebooks order by createtime asc"
  def rows = this.sql.rows(query)
  return rows 
}

ArrayList getPromotedBuilds() {
	def query = "select * from onebuildpromotion order by cijenkinsbuildid desc"
	def rows = this.sql.rows(query)
	return rows
}

boolean ratebookExists(ratebook) {
  def query = "select * from productionratebooks where bookname = '${ratebook.BookName}' and bookedition = '${ratebook.BookEdition}'"

  def rows = this.sql.rows(query)
  return (rows != [])
}

boolean insertAuditRecord(username, jobname, jobaction, build_url, environment_id, server_id, app_id, appserver_id) {
  def sql = "insert into jobaudits (username, jobname, jobaction, buildurl, environment_id, server_id, app_id, appserver_id, created_at, updated_at)"
  sql    += "values ('${username}', '${jobname}', '${jobaction}', '${build_url}', ${environment_id}, ${server_id}, ${app_id}, ${appserver_id}, sysdatetime(), sysdatetime())"

  def rows = this.sql.executeInsert(sql)
  return (rows != [])
}

def insertRatebook(ratebook) {
  def result = -1
  if (!ratebookExists(ratebook)) {
    def insert = "insert into productionratebooks(filename, bookname, bookedition, createtime, created_at, updated_at) values "
    insert += "('${ratebook.FileName}', '${ratebook.BookName}', '${ratebook.BookEdition}', '${ratebook.CreateTime}', sysdatetime(), sysdatetime())"

    def rows = this.sql.executeInsert(insert)
    result = rows[0][0]
  }
  return result
}

def insertBuild(app, jenkinsBuildNumber, jenkinsBuildURL, svnRev, artifactoryURL, buildID, createDate, svnPath, fromTrunk, storedInArtifactory, buildresult, description) {
  def insert = """
  insert into builds (
  appname,
  jenkinsbuildnumber,
  jenkinsbuildurl,
  svnrevisionnumber,
  artifactoryurl,
  buildidentifier,
  createdate,
  svnpath,
  trunk,
  storedinartifactory,
  buildresult,
  created_at,
  updated_at,
  description
  ) values ( 
  '${app}',
  ${jenkinsBuildNumber},
  '${jenkinsBuildURL}',
  ${svnRev},
  '${artifactoryURL}',
  '${buildID}',
  '${createDate}', 
  '${svnPath}',
  ${fromTrunk ? 1 : 0},
  ${storedInArtifactory ? 1 : 0},
  '${buildresult}',
  sysdatetime(),
  sysdatetime(),
  '${description}'
  )
  """
  def rows = this.sql.executeInsert(insert)

  return rows[0][0]
}

def insertBackupRecord(artifactoryUrl, appId, environmentname, sourcedatabaseserver, sourcedatabaseport, sourcedatabasename, description, appname, artifactname) {
  def insert = ""
  insert += "insert into backups (artifactoryurl, app_id, environmentname, sourcedatabaseserver, sourcedatabaseport, sourcedatabasename, created_at, updated_at, description, appname, artifactname) "
  insert += "values ("
  insert += "'${artifactoryUrl}',"
  insert += "${appId},"
  insert += "'${environmentname}',"
  insert += "'${sourcedatabaseserver}',"
  insert += "'${sourcedatabaseport}',"
  insert += "'${sourcedatabasename}',"
  insert += "sysdatetime(), sysdatetime(), '${description}', '${appname}', '${artifactname}'"
  insert += ")"

  def rows = this.sql.executeInsert(insert)

  return rows[0][0]
}

def insertRestoreHistoryRecord(app_id, backup_id) {

  def insert = ""
  insert += "insert into restorehistories (app_id, backup_id, created_at, updated_at) "
  insert += " values ( "
  insert += "${app_id}, "
  insert += "${backup_id}, "
  insert += "sysdatetime(), "
  insert += "sysdatetime())"

  def rows = this.sql.executeInsert(insert)

  return rows[0][0]
}

def insertNewGuidewireApp(app, database) {
  def currentDateTime = "sysdatetime()"

// query if database credential exists
def credentialExists = "select * from credentials "
credentialExists += "where username = '${database.username}' and password = '${database.password}'"
def credentialQuery = this.sql.rows(credentialExists)

def credentialID
// if not insert new credential
if (credentialQuery.size() == 0) {
def credentialInsert = "insert into credentials(username, password, created_at, updated_at) "
credentialInsert += "values ('${database.username}', '${database.password}', ${currentDateTime}, ${currentDateTime})"

def credentialInsertResponse = this.sql.executeInsert(credentialInsert)
credentialID = credentialInsertResponse[0][0]
} else {
  credentialID = credentialQuery[0].id
}

// query if database exists
def databaseExists = "select * from databasesettings "
databaseExists += "where databaseserver = '${database.server}' and databaseport = ${database.port} and databasename = '${database.name}'"
def databaseQuery = this.sql.rows(databaseExists)

def databaseID
// if not insert new database
if (databaseQuery.size() == 0) {
def databaseInsert = "insert into databasesettings(databaseserver, databaseport, databasename, credential_id, created_at, updated_at) "
databaseInsert += "values ('${database.server}', '${database.port}', '${database.name}', ${credentialID}, ${currentDateTime}, ${currentDateTime})"

def databaseInsertResponse = this.sql.executeInsert(databaseInsert)
databaseID = databaseInsertResponse[0][0]  
} else {
  databaseID = databaseQuery[0].id
}

// query if app exists
def appExists = "select * from apps "
appExists += "where name = '${app.name}' and appserver_id = ${app.appserver_id}"
def appQuery = this.sql.rows(appExists)

def appID
// if not insert new appserver
if (appQuery.size() == 0) {
def appInsert = "insert into apps(appserver_id, name, category, switchedoff, sso, batch, environment_id, databasesetting_id, contextroot, integrationpropertiespath, integrationpropertiestype, created_at, updated_at) "
appInsert += "values (${app.appserver_id}, '${app.name}', '${app.category}', 0, ${app.sso}, ${app.batch}, ${app.environment_id}, ${databaseID}, '${app.contextroot}', '${app.integrationpropertiespath}', '${app.integrationpropertiestype}', ${currentDateTime}, ${currentDateTime})"

def appInsertResponse = this.sql.executeInsert(appInsert)
appID = appInsertResponse[0][0]  
} else {
  appID = appQuery[0].id
}

def responseMap = [:]
responseMap.Credential = credentialID
responseMap.Database = databaseID
responseMap.App = appID

return responseMap
}

def insertNewProfile(environment, server, credential, appserver) {

def currentDateTime = "sysdatetime()"

// query if environment exists
def environmentExsists = "select * from environments where name = '${environment.name}'"
def environmentQuery = this.sql.rows(environmentExsists)

def environmentID
// if not insert new environment
if (environmentQuery.size() == 0) {
  def envrionmentInsert = "insert into environments(name, isproduction, retired, created_at, updated_at) "
  envrionmentInsert += "values ('${environment.name}', 0, 0, ${currentDateTime}, ${currentDateTime})"
  
  def envrionmentInsertResponse = this.sql.executeInsert(envrionmentInsert)
  environmentID = envrionmentInsertResponse[0][0]
  } else {
    environmentID = environmentQuery[0].id
  }

// query if server exists
def serverExists = "select * from servers "
serverExists += "where name = '${server.name}' and environment_id = ${environmentID} and domain = '${server.domain}'"
def serverQuery = this.sql.rows(serverExists)

def serverID
// if not insert new server
if (serverQuery.size() == 0) {
  def serverInsert = "insert into servers(name, environment_id, domain, created_at, updated_at) "
  serverInsert += "values ('${server.name}', ${environmentID}, '${server.domain}', ${currentDateTime}, ${currentDateTime})"
  
  def serverInsertResponse = this.sql.executeInsert(serverInsert)
  serverID = serverInsertResponse[0][0]  
  } else {
    serverID = serverQuery[0].id
  }

// query if credential exists
def credentialExists = "select * from credentials "
credentialExists += "where username = '${credential.username}' and password = '${credential.password}'"
def credentialQuery = this.sql.rows(credentialExists)

def credentialID
// if not insert new credential
if (credentialQuery.size() == 0) {
  def credentialInsert = "insert into credentials(username, password, created_at, updated_at) "
  credentialInsert += "values ('${credential.username}', '${credential.password}', ${currentDateTime}, ${currentDateTime})"
  
  def credentialInsertResponse = this.sql.executeInsert(credentialInsert)
  credentialID = credentialInsertResponse[0][0]  
  } else {
    credentialID = credentialQuery[0].id
  }

// query if appserver exists
def appserverExists = "select * from appservers "
appserverExists += "where name = '${appserver.name}' and server_id = ${serverID}"
def appserverQuery = this.sql.rows(appserverExists)

def appserverID
// if not insert new appserver
if (appserverQuery.size() == 0) {
  def appserverInsert = "insert into appservers(name, appservertype, credential_id, server_id, servicename, port, appport, nodename, profileroot, created_at, updated_at) "
  appserverInsert += "values ('${appserver.name}', '${appserver.type}', ${credentialID}, ${serverID}, '${appserver.servicename}', ${appserver.port}, ${appserver.appport}, '${appserver.nodename}', '${appserver.profileroot}', ${currentDateTime}, ${currentDateTime})"
  
  def appserverInsertResponse = this.sql.executeInsert(appserverInsert)
  appserverID = appserverInsertResponse[0][0]  
  } else {
    appserverID = appserverQuery[0].id
  }

  def responseMap = [:]
  responseMap.Environment = environmentID
  responseMap.Server = serverID
  responseMap.Credential = credentialID
  responseMap.AppServer = appserverID

  return responseMap
}

String updateAppWithDeploymentInfo(buildID, deploymentDate, buildUser, deploymentURL, appID) {
  def update = ""
  update += "update Apps set "
  update += "build_id=${buildID}, "
  update += "updated_at=sysdatetime()"
  update += "where id=${appID}"
  try{
    this.sql.execute(update)
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
this.sql.execute(insert)
} catch (Exception e) {
  return "Exception thrown: [${e}]"
}

return "Successful update"
}

String updateLastCheckTime() {
def update = "update globalconfiguration set value = cast(sysdatetime() as varchar(113)) where name='PING_LAST_CHECKED'"
try{
  this.sql.execute(update)
  } catch (Exception e) {
    return "Exception thrown: [${e}]"
  }

  return "Successful update"
}

String updateBuildTableForDeletedBuild(buildid) {
  String sql = ''
  sql += " update builds "
  sql += " set deleted=1, storedinartifactory=0, updated_at=sysdatetime() "
  sql += " where id = ? "

  try {
    this.sql.executeUpdate(sql, [buildid])
  }
  catch(Exception e) {
    throw e
  }
  return "Successful update"
}

  def runDML(dml) {
    try{
      this.sql.execute(dml)
    } catch (Exception e) {
      return "Exception thrown: [${e}]"
    }
    return 0
  }

  def runUpdate(dml) {
    try {
      def rowCount = this.sql.executeUpdate(dml)
      return rowCount
    }
    catch(Exception e) {
      throw e
    }
  }

  def runInsert(sql, paramsArray) {
    try {
      def keys = this.sql.executeInsert(sql, paramsArray)
      return keys
    }
    catch(Exception e) {
      throw e
    }
  }

  void close() {
    this.sql.close()
    this.sql = null
  }
}