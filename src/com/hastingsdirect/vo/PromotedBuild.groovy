package com.hastingsdirect.vo

class PromotedBuild implements Serializable {
	Number id
	String branch
	Number cijenkinsbuildid
	Number abjenkinsbuildid
	Number absvnrevisionnumber
	Number bcjenkinsbuildid
	Number bcsvnrevisionnumber
	Number ccjenkinsbuildid
	Number ccsvnrevisionnumber
	Number pcjenkinsbuildid
	Number pcsvnrevisionnumber
	String junitstatus
	String smoketeststatus
	String jobstatus
	Date created_at
	Date updated_at
	
}
