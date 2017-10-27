package com.hastingsdirect.sql

import com.hastingsdirect.vo.PromotedBuild
import groovy.sql.Sql

class RepositoryBuilds {

	public List<PromotedBuild> buildsOnePromoted() {
		Sql sql=CMDBConnection.createConnection();
		List<PromotedBuild> promoted=[]
		sql.eachRow('select * from onebuildpromotion'){ row->
			promoted<<row as PromotedBuild
		}

		sql.close()
		return promoted;
	}
}
