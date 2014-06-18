#!/bin/sh

# Choose your provider URL.
export PROVIDER_URL=rmi://localhost:1099
#export PROVIDER_URL=iiop://localhost:1196

# Choose your Database Driver Library
# DB_DRIVER=$JOTM_HOME/lib/mysql-connector-java-5.1.6-bin.jar
DB_DRIVER=$JOTM_HOME/lib/postgresql-8.0-310.jdbc3.jar

export CLI_CLASSPATH=\
.:\
$JOTM_HOME/lib/jotm-client.jar:\
$JOTM_HOME/lib/xapool.jar:\
$DB_DRIVER

#Uncomment this to run in debug mode.
#export DEBUG_OPT="-Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,server=y,address=1100,suspend=y"

# Usage:
#      java JdbcExample [database] [completion] [number]
#
# database can be:
# 	- postgresql 
# 	- mysql (example will look for a configuration file name [database].properties)
# completion can be:
# 	- commit
# 	- rollback
# number has to be a integer
java \
-Djava.naming.factory.initial=org.ow2.carol.jndi.spi.MultiOrbInitialContextFactory \
-Djava.naming.provider.url=$PROVIDER_URL \
-cp $CLI_CLASSPATH $DEBUG_OPT \
JdbcExample postgresql commit 28

java \
-Djava.naming.factory.initial=org.ow2.carol.jndi.spi.MultiOrbInitialContextFactory \
-Djava.naming.provider.url=$PROVIDER_URL \
-cp $CLI_CLASSPATH $DEBUG_OPT \
JdbcExample postgresql rollback 75
