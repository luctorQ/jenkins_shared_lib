package com.hastingsdirect.sql

import com.hastingsdirect.vo.PromotedBuild
import groovy.sql.Sql

class RepositoryBuilds implements Serializable{

	public List<PromotedBuild> buildsOnePromoted() {
		Sql sql=CMDBConnection.createConnection();
		List<PromotedBuild> promoted=[]
		def rows=sql.rows('select * from onebuildpromotion')
		rows.each({row->
			promoted<<row as HashMap
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
