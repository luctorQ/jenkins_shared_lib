import com.hastingsdirect.sql.RepositoryBuilds
import com.hastingsdirect.pipeline.PipelineUtils

def call(String branchRoot,Map appRevisions) {
	SVN_BRANCH=branchRoot
	repoBuilds=new RepositoryBuilds()

	println('buildAppsFromBranch. branchRoot:'+branchRoot+' appRevisions:'+appRevisions)

	def buildInfos=[:]

	def buildJobs=[:]
	appRevisions.each({appname,svnrevision->
		println 'buildAppsFromBranch. appname:'+appname+" svnrevision:"+svnrevision
		def inArtifactory=checkSvnRevisionInArtifactory(appname,svnrevision)
		def build=repoBuilds.getBuildByAppAndSvnRev(appname,svnrevision)
		if(inArtifactory) {
			buildInfos[appname]=build
		}else{
			println('buildAppsFromBranch.Build exists:'+build)
			/*			buildJobs['BuildApp_'+appname]= {
			 buildInfos[appname]=buildGwApp(appname,svnrevision)
			 }
			 */
			if(build) {
				buildInfos[appname]=build
			}else {
				buildJobs['BuildApp_'+appname]=  {
					buildInfos[appname]=buildGwApp(appname,svnrevision)
				}
			}
		}
	})

	parallel(buildJobs)

	println 'buildAppsFromBranch buildInfos:'+buildInfos
	return buildInfos
}

def buildGwApp(appName,svnRevToBuild="HEAD") {
	def JOB_NAME=PipelineUtils.jenkinsBuildJobNameFromApp(appName)
	//	def runWrapper=getJenkinsBuild('PC Build',621)
	def runWrapper=build(
			job:JOB_NAME,
			parameters:[
				string(name:"SVN_URL",value:"${SVN_BRANCH}/${PipelineUtils.getAppShortToLongName(appName)}/modules/configuration"),
				string(name:"VERSION_TO_BUILD",value:"${svnRevToBuild}"),
				string(name:"RUN_TESTS",value:"true"),
				string(name:"STORE_ARTIFACT",value:"Jenkins")
			],
			propagate:false)

	def jenkinsBuildId=runWrapper.getNumber();

	def build=repoBuilds.getBuildByAppnameJenkinsBuildId(appName,jenkinsBuildId)

	def testAction=runWrapper.rawBuild.actions.find{
		it instanceof hudson.tasks.junit.TestResultAction
	}
	def testsResult=[:]

	if(testAction) {
		testResult=[
			failCount:testAction.failCount,
			totalCount:testAction.totalCount,
			skipCount:testAction.skipCount,
			failureDiffString:testAction.failureDiffString,
			testsUrl:runWrapper.absoluteUrl+'/'+testAction.urlName
		]

		testAction=null
		dir('tmp_out'){
			writeFile file:'dummy', text:''
			step([$class: 'CopyArtifact',
				projectName: JOB_NAME,
				filter: 'out/test-classes/reports/html/junit-noframes.html',
				selector: [
					$class: 'SpecificBuildSelector',
					buildNumber:runWrapper.id
				],
				target:'junit_tests/'+appName,
				flatten:true
			]);
		}
	}

	eventsStore(
			msg:"Build ${build.jenkinsbuildnumber} of ${build.appname} completed with result ${build.buildresult}",
			type:"APP_BUILD_DONE",
			ref:[
				build:build,
				junittests:testResult
			])

	return build
}
