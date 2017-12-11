param (
  [Parameter(Mandatory=$true)][string]$identity,
  [Parameter(Mandatory=$true)][string]$members,
  [Parameter(Mandatory=$true)][string]$username,
  [Parameter(Mandatory=$true)][string]$password
)
Import-Module ActiveDirectory

$pw = convertto-securestring -AsPlainText -Force -String "$password"
$cred = new-object -typename System.Management.Automation.PSCredential -argumentlist "$username", $pw

Add-ADGroupMember -Identity $identity -Members $members -Credential $cred -Confirm:$false