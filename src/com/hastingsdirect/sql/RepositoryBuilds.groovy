package com.hastingsdirect.sql

import com.hastingsdirect.vo.PromotedBuild
import groovy.sql.Sql

class RepositoryBuilds implements Serializable{

	public List<PromotedBuild> buildsOnePromoted() {
		Sql sql=CMDBConnection.createConnection();
		List<PromotedBuild> promoted=[]
		sql.eachRow('select * from onebuildpromotion'){ row->
			println 'rrrrow:'+row
			promoted<<row as PromotedBuild
		}

		sql.close()
		return promoted;
	}
}
