@echo off

set KKMSERVER_HOME="C:\kkmserver"

echo Installing KkmServer...
C:\vagrant\resources\Setup_KkmServer.exe /SP- /VERYSILENT /SUPPRESSMSGBOXES /NOCANCEL /NORESTART /LOG=c:\kkmserver-install.log /DIR=%KKMSERVER_HOME%
copy C:\vagrant\resources\SettingsServ.ini %KKMSERVER_HOME%\Settings
C:\Windows\Microsoft.NET\Framework\v4.0.30319\InstallUtil.exe %KKMSERVER_HOME%\KkmServer.exe /LogFile=c:\kkmserver-service-install.log /LogToConsole=false
sc config KkmServer binpath= "%KKMSERVER_HOME%\KkmServer.exe RunAsService"
net start KkmServer
netsh advfirewall firewall add rule name="KkmServer" dir=in action=allow protocol=TCP localport=5893
echo ...done
