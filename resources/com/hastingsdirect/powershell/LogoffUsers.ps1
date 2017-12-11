param (
  [Parameter(Mandatory=$true)][string]$server
)

Function Get-Users($server) {
    $queryResults = query user /server:$server 2>$1
    If ($queryResults -match "No user exists") {return;}

    $starters = New-Object psobject -Property @{"SessionName" = 0; "UserName" = 0; "ID" = 0; "State" = 0; "Type" = 0; "Device" = 0;}
    Foreach ($result in $queryResults) {
        Try {
            If ($result.trim().substring(0, $result.trim().indexof(" ")) -eq "USERNAME") {
                $starters.UserName = $result.indexof("USERNAME");
                $starters.SessionName = $result.indexof("SESSIONNAME");
                $starters.ID = $result.indexof("ID");
                $starters.State = $result.indexof("STATE");
                Continue;
            }

            New-Object psobject -Property @{
                "Username" = $result.trim().substring(0, $result.trim().indexof(" ")).trim(">");
                "SessionName" = $result.Substring($starters.SessionName, $result.IndexOf(" ", $starters.SessionName) - $starters.SessionName);
                "ID" = $result.Substring($result.IndexOf(" ", $starters.SessionName), $starters.ID - $result.IndexOf(" ", $starters.SessionName) + 2).trim();
                "State" = $result.Substring($starters.State, $result.IndexOf(" ", $starters.State)-$starters.State).trim();
            }
        } Catch {
            $e = $_;
            Write-Log "ERROR: " + $e.PSMessageDetails
        }
    }
}

Function Logoff-Session($server, $id) {
    logoff $id /server:$server /V
}

$includeStates = '^(Disc|Active)$';
$sessions = Get-Users -server $server

If ($sessions) {
    Foreach ($session in $sessions) {
        If ($session.State -match $includeStates -and $session.Username -ne "") {
            Logoff-Session -server $server -id $session.id
        }
    }
} Else {
    Write-Output "No users on server [$server]"
    Exit 0
}