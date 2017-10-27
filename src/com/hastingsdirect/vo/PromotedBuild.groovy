package com.hastingsdirect.vo

class PromotedBuild implements Serializable {
	Integer id
	String branch
	Integer cijenkinsbuildid
	Integer abjenkinsbuildid
	Integer absvnrevisionnumber
	Integer bcjenkinsbuildid
	Integer bcsvnrevisionnumber
	Integer ccjenkinsbuildid
	Integer ccsvnrevisionnumber
	Integer pcjenkinsbuildid
	Integer pcsvnrevisionnumber
	String junitstatus
	String smoketeststatus
	String jobstatus
	Date created_at
	Date updated_at
}
