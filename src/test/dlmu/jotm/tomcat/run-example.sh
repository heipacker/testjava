#!/bin/sh

# set PATH to CATALINA_HOME
unset CATALINA_BASE
export CATALINA_HOME=$HOME/jakarta-tomcat-5.5.9

# compile example
ant

# copy jdbc driver in CATALINA
cp $JOTM_HOME/lib/mysql-*.jar $CATALINA_HOME/common/lib

# run example
cp $JOTM_HOME/examples/tomcat/output/dbtest.war $CATALINA_HOME/webapps
cp $JOTM_HOME/lib/jotm-client.jar $CATALINA_HOME/common/lib
cp $JOTM_HOME/lib/jotm-datasource.jar $CATALINA_HOME/common/lib
cp $JOTM_HOME/lib/xapool.jar $CATALINA_HOME/common/lib
cd $CATALINA_HOME/bin
./catalina.sh run &
sleep 4
echo "goto url http://localhost:8080/dbtest/test.jsp"
