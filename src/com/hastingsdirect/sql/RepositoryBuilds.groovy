package com.hastingsdirect.sql

import com.hastingsdirect.vo.PromotedBuild
import groovy.sql.Sql

class RepositoryBuilds extends Repository{

	public List buildsOnePromoted() {
		Sql sql=CMDBConnection.createConnection();
		List promoted=[]
		def rows=sql.rows('select * from onebuildpromotion order by "cijenkinsbuildid" desc')
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
