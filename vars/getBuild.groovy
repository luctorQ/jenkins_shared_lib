
def call(jobName,buildId) {
	return Jenkins.instance.getAllItems(Job)
			.find {job -> job.fullName == jobName }
			.getBuildByNumber(buildId)
	//			.getAction(hudson.model.ParametersAction)
}