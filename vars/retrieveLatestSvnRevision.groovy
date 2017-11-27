import com.hastingsdirect.pipeline.PipelineUtils


def call(appName,svnBranchRoot) {
	SVN_CRED_ID = 'SVC_SVN_CI'
	
	def svnRevision;
	withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: SVN_CRED_ID, passwordVariable: 'PWORD', usernameVariable: 'UNAME']]) {
		svnRevision=bat(returnStdout: true,
				script: "@svn info ${svnBranchRoot}/${PipelineUtils.getAppShortToLongName(appName)}/modules/configuration --show-item last-changed-revision --no-newline --username ${UNAME} --password ${PWORD}"
				).trim()
	}
	
	return svnRevision as Integer
}
