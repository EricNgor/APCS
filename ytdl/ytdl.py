# Eric Ngor
# Ease-of-use utility for youtube-dl

import os
import re
import sys

# Embed thumbnails from ytdl to their mp3 files
def embed():
    files = os.listdir('out')
    
    # Clean up temp and webm files
    for i in range(0, len(files)):
        if re.search("\\.webm", files[i]) or re.search("\\.temp\\.", files[i]):
            os.remove(f'out/{files[i]}')
    
    # Go through files 2 at a time
    # Convert then embed
    files = os.listdir('out')
    for i in range(0, len(files)-1):
        a = files[i]
        i += 1
        b = files[i]

        # thumbnail format cases
        patt1 = re.search('(.+?)\\.(jpg)', a)
        patt2 = re.search('(.+?)\\.(mp3|mp4)', a)
        if patt1:
            name1 = re.sub('(.+?)\\.(jpg)', patt1.group(1), a)
            name2 = re.sub('(.+?)\\.(mp3|mp4)', patt1.group(1), b)

            # Fix jpg resolution and bit depth
            os.chdir('out')
            os.system(f'magick "{a}" "temp.jpg"')
            os.remove(a)
            os.rename('temp.jpg', a)
            os.chdir('..')

        elif patt2:
            name1 = re.sub('(.+?)\\.(mp3|mp4)', patt2.group(1), a)
            name2 = re.sub('(.+?)\\.(webp)', patt2.group(1), b)

            # webp -> jpg
            jpg = re.sub('webp', 'jpg', b)
            os.chdir('out')
            os.system(f'magick "{b}" "{jpg}"')
            os.chdir('..')
        
        # Embedding
        if name1 == name2:
            if patt1:
                mp3 = b
                jpg = a
            elif patt2:
                mp3 = a
            
            embed = f'ffmpeg -i "{mp3}" -i "{jpg}" -map 0 -map 1 -c copy -id3v2_version 3 "temp.mp3"'
            os.chdir('out')
            print(f'[ytdl] Embedding: {embed}')
            os.system(embed)
            os.remove(mp3)
            os.rename('temp.mp3', mp3)
            os.chdir('..')

        # Names are not the same
        else:
            i -= 1

    # Sweep residual files (jpg, webp)
    files = os.listdir('out')

    for i in range(0, len(files)):
        if re.search("\\.(jpg|webp)", files[i]):
            os.remove(f'out/{files[i]}')

def main():
    # Create output directory if it does not already exist
    if (not os.path.isdir('out')):
        os.mkdir('out')

    while True:
        settings_ = input('Select an option: \n1) mp3\n2) mp4\n3) mp3 with thumbnail\n4) Embed\n')

        if settings_ == '1':
            settings = "-x --audio-format mp3 --add-metadata"
            break
        elif settings_ == '2':
            settings = "-f mp4 --add-metadata"
            break
        elif settings_ == '3':
            settings = "-x --audio-format mp3 --add-metadata --write-thumbnail"
            break
        elif settings_ == '4':
            embed()
            sys.exit()

    addl_ = input('Additional args? \n1) No\n2) Playlist Start/End\n3) Playlist Indices\n')

    if addl_ == '2':
        start = input('Start? ')
        end = input('End? ')
        if start:
            settings += f' --playlist-start {start}'
        if end:
            settings += f' --playlist-end {end}'
    elif addl_ == '3':
        items = input('Enter indices separated by commas, or as a range with a hyphen: ')
        if items:
            settings += f' --playlist-items {items}'

    url = input('URL: ')
    command = f'youtube-dl {settings} "{url}"'
    print(f'[ytdl] Executing command: \n{command}')
    os.chdir('out')
    os.system(command)
    os.chdir('..')

    if settings_ == '3':
        embed()

if __name__ == "__main__":
    main()