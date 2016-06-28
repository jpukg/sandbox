#install the application
deployEAR="C:\Users\Desktop\scripts\ear\\myAPP.ear"
appName="myAPP"
attr="-appname " + appName + " "
AdminApp.install(deployEAR, "["+attr+"]" );
#save
AdminConfig.save();