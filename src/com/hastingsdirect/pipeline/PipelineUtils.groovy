package com.hastingsdirect.pipeline

import java.util.Map
import com.hastingsdirect.sql.RepositoryBuilds
import org.boon.Boon
import org.boon.json.JsonFactory;

class PipelineUtils {


	static Map getJenkinsConfiguration(String server) {
		RepositoryBuilds repoBuilds=new RepositoryBuilds()
		def globalConfigMap = repoBuilds.getGlobalConfigByServerName("Global")
		def serverConfigMap = repoBuilds.getGlobalConfigByServerName(server)

		println 'globalConfigMap:'+globalConfigMap
		println 'serverConfigMap'+serverConfigMap

		def configMap = [:]
		for (row in globalConfigMap) {
			configMap["${row.name}"] = row.value
		}

		for (row in serverConfigMap) {
			configMap["${row.vame}"] = row.value
		}
		return configMap
	}

	static String lockAppServerName(servername,appservername) {
		return "${servername}_${appservername}"
	}

	static String getArtifactName(shortAppName) {
		def packageName = [:]
		packageName['ab'] = "ContactManager-dbcp.ear"
		packageName['bc'] = "BillingCenter-dbcp.ear"
		packageName['cc'] = "ClaimCenter-dbcp.ear"
		packageName['pc'] = "PolicyCenter-dbcp.ear"
		packageName['ec'] = "Portal.ear"
		packageName['isl'] = "ISL.zip"
		packageName['pss'] = "PSS.zip"
		packageName['apigw'] = "output.xml"
		return packageName[shortAppName]
	}

	static String jenkinsBuildJobNameFromApp(app) {
		def jobName = [:]
		jobName['ab'] = "CM Build"
		jobName['bc'] = "BC Build"
		jobName['cc'] = "CC Build"
		jobName['pc'] = "PC Build"
		return jobName[app]
	}

	static String getAppShortToLongName(shortApp) {
		def longAppName = [:]
		longAppName['ab'] = "ContactManager"
		longAppName['bc'] = "BillingCenter"
		longAppName['cc'] = "ClaimCenter"
		longAppName['pc'] = "PolicyCenter"
		longAppName['ec'] = "EdgeConnect Portal"
		longAppName['isl'] = "IntegrationServiceLayer"
		longAppName['pss'] = "PortalSecurity"
		return longAppName[shortApp]
	}

	static String returnSQLDateTimeString() {
		def str = ""

		def cal = Calendar.getInstance()

		def dd = cal.get(Calendar.DAY_OF_MONTH)
		def MM = cal.get(Calendar.MONTH) + 1
		def yyyy = cal.get(Calendar.YEAR)
		def HH = cal.get(Calendar.HOUR_OF_DAY)
		def mm = cal.get(Calendar.MINUTE)
		def ss = cal.get(Calendar.SECOND)

		cal = null

		return "${yyyy}-${MM}-${dd} ${HH}:${mm}:${ss}"
	}

	static List buildCause(rawBuild) {
		def causes=rawBuild.getCauses()
		def list=[]
		causes.each({ list<<it.shortDescription })
		return list
	}
	static isStartedFromUpstream(rawBuild) {
		return rawBuild.getCause(hudson.model.Cause.UpstreamCause)!=null
	}
	


	/**
	 * Create List,Map object from jsonString
	 * @param jsonString - string form of json
	 * @param serialized - set to true if required use as variable in pipeline (probably due to exception java.io.NotSerializableException: org.boon.core.value.LazyValueMap)
	 * @return
	 */
	static Object fromJson(String jsonString,boolean serialized=false) {
		def jsonobject= Boon.fromJson(jsonString)
		if(serialized) {
			return convertJsonToSerialized(jsonobject)
		}else {
			return jsonobject
		}
	}

	static String toJson(Object value) {
		return JsonFactory.toJson(value)
	}

	/**
	 * convert jsonobject created by fromJson to serialized form - necessary if need to define variable in pipeline
	 * @param val
	 * @return serialized form of jsonobject
	 */
	static Object convertJsonToSerialized(Object jsonParsed){
		if(jsonParsed instanceof List) {
			def list=[]
			jsonParsed.each({ list<<convertJsonToSerialized(it) })
			return list
		}else if (jsonParsed instanceof Map){
			def map=[:]
			jsonParsed.each({key,value->
				map[key]=convertJsonToSerialized(value)
			})
			return map
		}else {
			return jsonParsed
		}
	}
}
