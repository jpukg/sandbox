@echo off
echo Downloading dependencies for Windows 64-bit...
wget\wget.exe -c -t 0 -i wget\downloads-win64.txt

IF EXIST eclipse (
echo Deleting eclipse...
RD /S /Q "eclipse"
)
echo Extracting eclipse workspace...
unzip -q apache-maven-3.3.9-bin.zip
echo Finished eclipse setup.
