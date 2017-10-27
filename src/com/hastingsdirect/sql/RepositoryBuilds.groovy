package com.hastingsdirect.sql

import com.hastingsdirect.vo.PromotedBuild
import groovy.sql.Sql

class RepositoryBuilds implements Serializable{

	private Map rowAsMap(Map row) {
		def rowMap = [:]
		row.keySet().each {column ->
			rowMap[column] = row[column]
		}
		return rowMap
	}
	
	public List<PromotedBuild> buildsOnePromoted() {
		Sql sql=CMDBConnection.createConnection();
		List<PromotedBuild> promoted=[]
		def rows=sql.rows('select * from onebuildpromotion')
		rows.each({row->
			promoted<<rowAsMap(row)
		})
		
/*		sql.eachRow('select * from onebuildpromotion'){ row->
			promoted<<row as Map
		}
*/
		rows=null;
		sql.close()
		return promoted;
	}
}
