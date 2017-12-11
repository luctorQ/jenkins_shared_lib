def call(server) {
  def scriptfile = "LogoffUsers.ps1"

  def psscript = libraryResource "com/hastingsdirect/powershell/${scriptfile}"
  writeFile(file:scriptfile, text:psscript)

  def workspace = pwd()

  def script = "& \"${workspace}/${scriptfile}\" -server \"${server}\""
  powershell(script: script)
}