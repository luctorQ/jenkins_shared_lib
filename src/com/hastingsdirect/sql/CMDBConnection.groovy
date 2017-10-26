import groovy.sql.Sql

public class CMDBConnection {
	/*	def url = "jdbc:sqlserver://bx1-prd-sql01.network.uk.ad:1433;databaseName=RMCMDB"
	 def user = "JenkinsUser"
	 def password = "ds"
	 def driver = 'com.microsoft.sqlserver.jdbc.SQLServerDriver'
	 */	

	def url ="jdbc:h2:~/things"
	def user="sa"
	def password= "sa"
	def driver= "org.h2.Driver"

	static Sql createConnection() {
		CMDBConnection conn=new CMDBConnection()
		return groovy.sql.Sql.newInstance(conn.url,conn.user,conn.password,conn.driver)
	}

	static void execute(Closure c) {
		CMDBConnection conn=new CMDBConnection()
		Sql.withInstance(conn.url,conn.user,conn.password,conn.driver, c);
	}
}
