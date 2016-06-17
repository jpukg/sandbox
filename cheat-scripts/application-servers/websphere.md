Websphere Application Server
=====

#### How to decode WAS password

#### WAS 6
	java -classpath <WASHOME>/deploytool/itp/plugins/com.ibm.websphere.v61_6.1.200/ws_runtime.jar com.ibm.ws.security.util.PasswordDecoder "<password starting with {xor}>"

#### WAS 8
	java -classpath <WASHOME>/deploytool/itp/plugins/com.ibm.websphere.v85_2.0.0.v20120621_2102/wasRuntimeUtilV85.jar com.ibm.ws.security.util.PasswordDecoder "<password starting with {xor}>"
	