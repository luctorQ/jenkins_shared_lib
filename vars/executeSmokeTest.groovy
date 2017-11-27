
def call(String CI_ENVIRONMENT) {
	def jobResult
	try {
		retry(2){
			//first test after PC QH restart may fail due to caches
			jobResult=build job:'Guidewire Smoke Test',
			parameters:[string(name:"ENVIRONMENT",value:CI_ENVIRONMENT)],
			propagate: false

			if(jobResult.result!="SUCCESS") {
				throw new hudson.AbortException("ERROR: Smoke Test attempt failure")
			}
		}
	}catch(error) {
		println 'Smoke Test failed maximum attempt reached'
		eventsStore(msg:"Smoke test on ${CI_ENVIRONMENT} faled after maximum attempt reached",type:'SMOKE_TESTED_FAIL')
	}

	//get smoke test result from archived
	dir('tmp_out'){
		writeFile file:'dummy', text:''
		step([$class: 'CopyArtifact',
			projectName: 'Guidewire Smoke Test',
			filter: 'HtmlReport/**,eTAFSuite.log',
			selector: [
				$class: 'SpecificBuildSelector',
				buildNumber:jobResult.id
			],
			target:'smoke_tests',
			flatten:true
		]);
		script{
			if(fileExists(file:'smoke_tests')) {
				zip zipFile:'report_smoke.zip',dir:'smoke_tests'
			}
		}
	}

	eventsStore(
			msg:"Smoke test on ${CI_ENVIRONMENT} finished with result ${jobResult.result}",
			type:'SMOKE_TESTED',
			ref:[
				result:jobResult.result,
				absoluteUrl:jobResult.absoluteUrl,
				displayName:jobResult.displayName
			])
	
	return jobResult.result
}