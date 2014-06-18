# tomcat example
# $Id$

Scenario:
---------
The Tomcat example shows how to integrate and use JOTM with Tomcat to provide
Transaction support to Servlets.

Tomcat is the servlet container used in the official Reference Implementation 
for the Java Servlet and JavaServer Pages (JSP) technologies. 
Tomcat is released under the Apache Software License

Components of this example:
o JOTM as the Transaction Manager.
o Tomcat 5.5 as the Servlet container. Other versions of tomcat should work, but
  the configuration should be adapted. (war format, context.xml file, ...)
o A database (MySQL by default, but any database can be used)

The JOTM is started by the DataBaseFactory declared in context.xml, when
the class is loaded (static bloc). It is collocated to the tomcat JVM.
This is triggered by the factory tag of the Resource element. Default value
for factory would be the Jakarta Commons factory that is not transacted.

MySQL Setup:
------------
The database setup is the same than the JDBC example (see
examples/jdbc/README.txt for more information)

You need to adapt the build.xml to give the correct path to your
database driver. Default values are :
  <property name="driverdir" value="${basedir}"/>
  <property name="drivername" value="mysql-connector-java-5.1.6-bin.jar"/>

You must also modify the context.xml file with the correct Driver ClassName,
and url. default are :
        driverClassName="com.mysql.jdbc.Driver"
        url="jdbc:mysql://localhost/javatest" />

Tomcat setup:
------------
set the CATALINA_HOME on your tomcat install directory, for example:
export CATALINA_HOME=$HOME/jakarta-tomcat-5.5.9
unset CATALINA_BASE

See http://tomcat.apache.org/tomcat-5.5-doc/index.html  for detailed
information about tomcat. Information about JNDI Resources can be found in :
  http://tomcat.apache.org/tomcat-5.5-doc/jndi-resources-howto.html

Put these libraries in $CATALINA_HOME/common/lib :
  cp $JOTM_HOME/lib/carol-*.jar $CATALINA_HOME/common/lib
  cp $JOTM_HOME/lib/howl-*.jar $CATALINA_HOME/common/lib
  cp $JOTM_HOME/lib/jotm-core.jar $CATALINA_HOME/common/lib
  cp $JOTM_HOME/lib/jotm-datasource.jar $CATALINA_HOME/common/lib
  cp $JOTM_HOME/lib/ow2-connector-1.5-*.jar $CATALINA_HOME/common/lib
  cp $JOTM_HOME/lib/ow2-jta-1.1-spec-*.jar $CATALINA_HOME/common/lib
  cp $JOTM_HOME/lib/xapool-*.jar $CATALINA_HOME/common/lib

Compilation and creation of the war file:
----------------------------------------
cd $JOTM_HOME/examples/tomcat
ant

generated files are put in the JOTM_HOME/examples/tomcat/output:
 -> JOTM_HOME/examples/tomcat/output/dbtest.war

To run the example:
-------------------
Deploy the web application :
  cp $JOTM_HOME/examples/tomcat/output/dbtest.war $CATALINA_HOME/webapps

Start tomcat :
  cd $CATALINA_HOME/bin
  ./catalina.sh run

Browse the example :
  Go in your browser to the URL

     http://localhost:8080/dbtest/test.jsp

  Choose if you want to commit or rollback the transaction:
     o if you choose "commit", the integer value stored in the database is
       incremented
     o if you choose "rollback", the value in the database does not change

Debugging the example:
----------------------
Jotm uses log4j as debug system.
To set traces in tomcat/jotm, process as following:

o Configure a log4j.properties file and copy it in $CATALINA_HOME/common/classes
  You can start from the file : $JOTM_HOME/conf/log4j.properties
o Copy a log4j jar file in $CATALINA_HOME/common/lib
  for ex: log4j-1.2.15.jar
o Copy a commons-logging jar file in $CATALINA_HOME/common/lib
  for ex: commons-logging-1.1.1.jar
o restart tomcat:
  cd $CATALINA_HOME/bin; ./catalina.sh run

jotm traces are written in the file you choose in log4j.properties, for example:
  log4j.appender.Requests.File=${catalina.home}/logs/jotm.log