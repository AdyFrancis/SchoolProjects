$olFolderInbox = 6
$outlook = new-object -com outlook.application;
$ns = $outlook.GetNameSpace("MAPI");
$inbox = $ns.GetDefaultFolder($olFolderInbox)
$targetFolder = $inbox.Folders | Where-Object {$_.name -eq "SDSTest Results"}
$targetFolder.items | foreach {
    "$($_.Subject)" | out-file Z:\inbox.csv -Append
}