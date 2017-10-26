package com.hastingsdirect.sql

//@GrabConfig(systemClassLoader=true)
//@Grab(group='com.h2database', module='h2', version='1.3.176')

import groovy.sql.Sql

class CMDBConnection {
	/*	def url = "jdbc:sqlserver://bx1-prd-sql01.network.uk.ad:1433;databaseName=RMCMDB"
	 def user = "JenkinsUser"
	 def password = "ds"
	 def driver = 'com.microsoft.sqlserver.jdbc.SQLServerDriver'
	 */	

	def url =" jdbc:h2:tcp://192.168.56.1:9092/~/teste"
	def user="sa"
	def password= "sa"
	def driver= "org.h2.Driver"

	static Object clazzzz() {
		def cl=Class.forName("org.h2.Driver")
		println "class for name:"+cl
		return cl
	}

	
	static Sql createConnection() {
		def cl=Class.forName("org.h2.Driver")
		println "class for name:"+cl
		CMDBConnection conn=new CMDBConnection()
//		return groovy.sql.Sql.newInstance(conn.url,conn.user,conn.password,conn.driver)
		return groovy.sql.Sql.newInstance('jdbc:h2:tcp://192.168.56.1:9092/~/teste','sa','sa','org.h2.Driver')
	}

	static void execute(Closure c) {
		CMDBConnection conn=new CMDBConnection()
		Sql.withInstance(conn.url,conn.user,conn.password,conn.driver, c);
	}
}
