package com.hastingsdirect.sql

import com.hastingsdirect.vo.PromotedBuild
import groovy.sql.Sql

class RepositoryBuilds extends Repository{

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
		sql.close()
		return promoted;
	}
}
