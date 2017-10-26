package com.hastingsdirect.sql

import groovy.sql.Sql

class CMDBConnection implements Serializable {
	/*	def url = "jdbc:sqlserver://bx1-prd-sql01.network.uk.ad:1433;databaseName=RMCMDB"
	 def user = "JenkinsUser"
	 def password = "ds"
	 def driver = 'com.microsoft.sqlserver.jdbc.SQLServerDriver'
	 */	

	String url ="jdbc:h2:tcp://192.168.56.1:9092/~/teste"
	String user="sa"
	String password= "sa"
	String driver= "org.h2.Driver"

	
	String toString() {
		return "url:${this.url},user:${this.user},password:${this.password},driver:${this.driver}"
	}

	
	static Sql createConnection() {
		def cl=Class.forName("org.h2.Driver")
		println "class for name:"+cl
		CMDBConnection conn=new CMDBConnection()
		println 'connnn driver:'+conn.driver+' url:'+conn.url
		return groovy.sql.Sql.newInstance(conn.url,conn.user,conn.password,conn.driver)
//		return groovy.sql.Sql.newInstance('jdbc:h2:tcp://192.168.56.1:9092/~/teste','sa','sa','org.h2.Driver')
	}

	static void execute(Closure c) {
		CMDBConnection conn=new CMDBConnection()
		Sql.withInstance(conn.url,conn.user,conn.password,conn.driver, c);
	}
}
