package com.hastingsdirect.sql

import java.util.Map

abstract class Repository implements Serializable{
	
	Map rowAsMap(Map row) {
		def rowMap = [:]
		row.keySet().each {column ->
			rowMap[column] = row[column]
		}
		return rowMap
	}
}
