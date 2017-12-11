def call(credentials_id, identity, members) {
  def scriptfile = "RemoveADGroupMember.ps1"

  def psscript = libraryResource "com/hastingsdirect/powershell/${scriptfile}"
  writeFile(file:scriptfile, text:psscript)

  def workspace = pwd()

  withCredentials([usernamePassword(credentialsId: credentials_id, passwordVariable: 'PWORD', usernameVariable: 'UNAME')]) {
    def script = "& \"${workspace}/${scriptfile}\" -identity \"${identity}\" -members ${members} -username ${UNAME} -password ${PWORD}"
    powershell(script: script)
  }
}