j# Eric Ngor
# Ease-of-use utility for youtube-dl

# Create the output directory if it does not already exist
if (!(Test-Path -path out)) {
    New-Item -Path . -Name "out" -ItemType "directory" > $null
}

$settings? = Read-Host "Select an option:`n1) mp3`n2) mp4`n3) mp3 with thumbnail`n"

Do {
    Switch ($settings?) {
        1 { $settings = "-x --audio-format mp3 --add-metadata"; $valid = $true }
        2 { $settings = "-f mp4 --add-metadata"; $valid = $true }
        3 { $settings = "-x --audio-format mp3 --add-metadata --write-thumbnail"; $valid = $true }
        default { $settings? = Read-Host "Enter a valid setting" }
    }
} While (!($valid)) 

$addl? = Read-Host "Additional args?`n1) No`n2) Playlist Start/End`n3) Playlist Indices`n"

Switch ($addl?) {
    1 {}
    2 {
        $start = Read-Host "Start?"
        $end = Read-Host "End?"
        if ($start) { $settings = $settings + " --playlist-start $start" }
        if ($end) { $settings = $settings + " --playlist-end $end" }
    }
    3 {
        $items = Read-Host "Enter indices separated by commas, or as a range with a hyphen"
        if ($items) { $settings = $settings + " --playlist-items $items" }
    }
    default {}
}

$url = Read-Host "URL"
$ex = "& ../youtube-dl $settings `"$url`""
Invoke-Expression "& cd out"
Write-Output "Invoking:`n$ex"
Invoke-Expression $ex

Invoke-Expression "& cd .."

# Call embed.ps1
if (($settings? -eq 3)) { .\embed.ps1 }

[void](Read-Host "done")