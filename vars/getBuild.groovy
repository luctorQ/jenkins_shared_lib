
def call(jobName,buildId) {
	def job=Jenkins.instance.getAllItems(Job)
			.find {job -> job.fullName == jobName }
			.getBuildByNumber(buildId)
	//			.getAction(hudson.model.ParametersAction)
	
	if(job!=null) {
		return new org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper(job)
	}else {
		return null
	}
}