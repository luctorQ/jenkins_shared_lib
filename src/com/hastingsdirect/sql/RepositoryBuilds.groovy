package com.hastingsdirect.sql

import com.hastingsdirect.vo.PromotedBuild
import groovy.sql.Sql

class RepositoryBuilds implements Serializable{

	public List<PromotedBuild> buildsOnePromoted() {
		Sql sql=CMDBConnection.createConnection();
		List<PromotedBuild> promoted=[]
		def rows=sql.rows('select * from onebuildpromotion')
		rows.each({row->
			promoted<<row as PromotedBuild
		})
		
/*		sql.eachRow('select * from onebuildpromotion'){ row->
			println 'rrrrow:'+row
			promoted<<row[0] as PromotedBuild
		}
*/
		sql.close()
		return promoted;
	}
}
