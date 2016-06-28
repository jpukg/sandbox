#!
import sys

appName="myApp"
appManager=AdminControl.queryNames('cell=srvwasvecCell01,node=srvwasvecNode01,type=ApplicationManager,process=server1,*')
print appManager
AdminControl.invoke(appManager, 'startApplication', appName)