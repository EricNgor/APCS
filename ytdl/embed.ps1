# Eric Ngor
# Embed thumbnails from ytdl to their mp3 files

# Remove the .temp. files
$found = $false
$files = Get-ChildItem ".\out"

# webm file cleanup
for ($i = 0; $i -lt $files.Count; $i++) {
    if ($files[$i] -match "\.webm") {
        Remove-Item -LiteralPath $($files[$i]).FullName
    }
}

# Now there should be no more temp files

# Go through files 2 at a time
# Convert then embed
$files = Get-ChildItem ".\out"
for ($i = 0; $i -lt $files.Count; ++$i) {
    $a = $files[$i].FullName    # first file
    $b = $files[++$i].FullName  # second file
    $order = -1

    # thumbnail format cases
    if ($a -match '(.+?)\.(jpg)') {
        $name1 = $a -replace '(.+?)\.(jpg)', '$1'
        $name2 = $b -replace '(.+?)\.(mp3|mp4)', '$1'
        $order = 0

        # Fix jpg resolution and bit depth
        Invoke-Expression "& magick `"$a`" 'temp.jpg'"
        Remove-Item -LiteralPath $a
        Invoke-Expression "& mv 'temp.jpg' `"$a`""
    } 
    else {
        $name1 = $a -replace '(.+?)\.(mp3|mp4)', '$1'
        $name2 = $b -replace '(.+?)\.(webp)', '$1'
        $order = 1
    }
    
    if ($name1 -eq $name2) {
        if ($order -eq 1) {
            # webp -> jpg
            $jpg = $b -replace 'webp', 'jpg'
            $convert = "& magick `"$b`" `"$jpg`""
            Invoke-Expression $convert
        }

        Switch ($order) {
            0 { $mp3 = $b; $jpg = $a }
            1 { $mp3 = $a }
        }

        $embed = "& ffmpeg -i `"$mp3`" -i `"$jpg`" -map 0 -map 1 -c copy -id3v2_version 3 temp.mp3"
        Invoke-Expression $embed
        Remove-Item -LiteralPath $mp3
        Invoke-Expression "& mv 'temp.mp3' `"$mp3`""
    }
    # names are not equal
    else { --$i }
}

# Sweep residual files

$files = Get-ChildItem ".\out"
for ($i = 0; $i -lt $files.Count; $i++) {
    if ($files[$i] -match "\.(jpg|webp)") {
        Remove-Item -LiteralPath $($files[$i]).FullName
        $found = $true
    }
}
