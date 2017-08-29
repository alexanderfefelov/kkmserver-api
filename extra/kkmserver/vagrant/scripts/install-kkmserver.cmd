@echo off

set KKMSERVER_HOME="C:\kkmserver"

echo Installing KkmServer...
msiexec /i C:\vagrant\resources\Setup_KkmServer.msi /quiet /qn /norestart /log c:\kkmserver-install.log APPDIR=%KKMSERVER_HOME%
copy C:\vagrant\resources\SettingsServ.ini %KKMSERVER_HOME%
%KKMSERVER_HOME%\InstallUtil.exe %KKMSERVER_HOME%\KkmServer.exe
net start KkmServer
netsh advfirewall firewall add rule name="KkmServer" dir=in action=allow protocol=TCP localport=5893
echo ...done
