package com.hastingsdirect.ep;

import com.hastingsdirect.sql.RepositoryBuilds

class ExtendedProperties implements Serializable {
	
	public ExtendedProperties() {
		def builds=new RepositoryBuilds()
		builds.buildsOnePromoted()
	}
	
	public String test() {
		return "EP test"
	}
	
	public String test2() {
		return "test 2"
	}
}
