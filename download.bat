REM powershell -NoProfile -ExecutionPolicy unrestricted -Command "(new-object System.Net.WebClient).DownloadFile('https://www.python.org/ftp/python/2.7.11/python-2.7.11.msi', 'python-2.7.11.msi')"
REM msiexec /i "python-2.7.11.msi" /passive TARGETDIR="C:\tools\python2-x86_32" || echo "32bit python install failed" && exit /b 1

REM powershell -NoProfile -ExecutionPolicy unrestricted -Command "(new-object System.Net.WebClient).DownloadFile('http://a.mbbsindia.com/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.zip', 'apache-maven-3.3.9-bin.zip')"
 