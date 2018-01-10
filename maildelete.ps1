$olFolderInbox = 6

$outlook = new-object -com outlook.application;

$ns = $outlook.GetNameSpace("MAPI");
$inbox = $ns.GetDefaultFolder($olFolderInbox)
$targetFolder = $inbox.Folders | Where-Object {$_.name -eq "SDSTest Results"}
$subjects = Get-Content Z:\subjects.txt

$count = 0
$lines = $subjects.Count
echo $lines
while ($count -ne $lines) {
    $targetFolder.items | foreach {
        $subj = "$($_.Subject)"    
        $contains = $subjects -contains $subj
        if ($contains -eq $true) {
            $count++
            #$subjects | Where-Object {$_ -match $subj} | Set-Content .\newsubjects.txt
            $($_).delete()
        }
    }
}
echo "$count emails deleted."
