
/**
 * returns RunWrapper instance by name and id 
 * @link org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper
 * @link org.jenkinsci.plugins.workflow.job.WorkflowRun 
 * @author luszynp
 *
 */

def call(String jobName,Number buildId) {
	def job=Jenkins.instance.getAllItems(Job)
            .find {job -> job.fullName == jobName }
            .getBuildByNumber(buildId)
    //          .getAction(hudson.model.ParametersAction)
    
    if(job!=null) {
        return new org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper(job,false)
    }else {
        return null
    }
}
