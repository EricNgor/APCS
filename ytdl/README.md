**ytdl**

Ease-of-use script for youtube-dl  
***

**Associated files**  
*ytdl.py* - combined, independent version of ytdl.ps1 and embed.ps1  
*ytdl.ps1* - main youtube-dl handler  
*embed.ps1* - embeds thumbnails into .mp3 files; called by ytdl.ps1  

**Dependencies**  
Python  
[youtube-dl]( https://ytdl-org.github.io/youtube-dl/download.html)  
[ffmpeg]( https://ffmpeg.org) (with ffplay.exe, ffprobe.exe)  
[ImageMagick]( https://imagemagick.org/index.php)

**Known issue**
* program may occasionally freeze due to attempting to manage a corrupted file
  * tends to happen when trying to download more obscure videos
  * may need to restart computer to fix if force removing file does not work

**Other**  
* ImageMagick is only required if you intend to embed thumbnails (make sure the *magick* function is in your Path variable)

* In order to run PowerShell scripts, enter the following command in PowerShell (ran w/ Administrator):  
 *set-executionpolicy remotesigned*

* [youtube-dl documentation]( https://github.com/ytdl-org/youtube-dl)