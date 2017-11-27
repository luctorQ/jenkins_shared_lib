package com.hastingsdirect.sql

import groovy.sql.Sql

class CMDBConnection implements Serializable {
		
	def url = "jdbc:sqlserver://bx1-prd-sql01.network.uk.ad:1433;databaseName=RMCMDB"
	def user = "JenkinsUser"
	def password = "Ak5TLEIB5HSDSB2otvN0ONdejwLkXtup!"
	def driver = 'com.microsoft.sqlserver.jdbc.SQLServerDriver'
		
/*
	String url ="jdbc:h2:tcp://172.19.128.17:9092/~/teste" //just for local tests on Jenkins04 box
	String user="sa"
	String password= ""
	String driver= "org.h2.Driver"

*/	

	
	static Sql createConnection() {
//		def cl=Class.forName("org.h2.Driver")
//		println "class for name:"+cl
		CMDBConnection conn=new CMDBConnection()
		println 'connnn driver:'+conn.driver+' url:'+conn.url
		return groovy.sql.Sql.newInstance(conn.url,conn.user,conn.password,conn.driver)
	}

	static void execute(Closure c) {
		CMDBConnection conn=new CMDBConnection()
		Sql.withInstance(conn.url,conn.user,conn.password,conn.driver, c);
	}
}
