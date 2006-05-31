Apache Axis (http://ws.apache.org/axis/) needs to be installed as a separate webapp under tomcat to expose
Centric CRMs Web Service Interface.

Steps to enable Centric CRMs Web Service:

1. Stop Tomcat

2. Download and install apache axis 1.2 from the following URL and deploy it
into your tomcat server:

http://www.wmwweb.com/apache/ws/axis/1_2_1/

3. Compile Centric CRM using "ant deploy"

4. Copy Centric CRM libraries aspcfs.jar and darkhorseventures.jar into "axis/WEB-INF/lib/" folder

5. Start Tomcat

6. Deploy the webservice "org.aspcfs.apps.axis.CentricService", using axis AdminClient tool. This will
require the deployment descriptor file deploy.wsdd.

A typical invocation of the AdminClient looks like this:

% export AXIS_HOME=~/axis-1_2_1
% export AXIS_LIB=/home/webapps/axis/WEB-INF/lib
% export AXIS_CP=$AXIS_LIB/axis.jar
% export AXIS_CP=$AXIS_CP:$AXIS_LIB/commons-discovery-0.2.jar
% export AXIS_CP=$AXIS_CP:$AXIS_LIB/commons-logging-1.0.4.jar

% export AXIS_CP=$AXIS_CP:$AXIS_LIB/jaxrpc.jar
% export AXIS_CP=$AXIS_CP:$AXIS_LIB/saaj.jar
% export AXIS_CP=$AXIS_CP:$CATALINA_HOME/common/lib/servlet-api.jar

% export AXIS_CP=$AXIS_CP:$AXIS_LIB/log4j-1.2.8.jar
% export AXIS_CP=$AXIS_CP:$AXIS_LIB/xml-apis.jar
% export AXIS_CP=$AXIS_CP:$AXIS_LIB/xercesImpl.jar
% export AXIS_CP=$AXIS_CP:$AXIS_LIB/wsdl4j-1.5.1.jar

% export AXIS_CP=$AXIS_CP:$AXIS_HOME/WEB-INF/classes
% export AXIS_CP=$AXIS_CP:$AXIS_HOME/WEB-INF/lib/aspcfs.jar
% export AXIS_CP=$AXIS_CP:$AXIS_HOME/WEB-INF/lib/darkhorseventures.jar

% java -cp $AXIS_CP org.apache.axis.client.AdminClient -h127.0.0.1 -p80 deploy.wsdd

You should see:

<Admin>Done processing</Admin>

This command has now made the Centric CRM service accessible via SOAP.

7. The web sevice interface can now be accessed from the following URL
http://127.0.0.1:8080/axis/services/CentricService?wsdl

8. The web service methods can be tested by accessing a URL of the form
http://127.0.0.1:8080/axis/services/CentricService?method=validateUser&username=ananth&password=yourcleartextpwd



