#!/bin/sh

# Choose your provider URL.
export PROVIDER_URL=rmi://localhost:1099
#export PROVIDER_URL=iiop://localhost:1196

export CLI_CLASSPATH=\
.:\
$JOTM_HOME/lib/jotm-client.jar

#Uncomment this to run in debug mode.
#export DEBUG_OPT="-Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,server=y,address=1100,suspend=y"

java \
-Djava.naming.factory.initial=org.ow2.carol.jndi.spi.MultiOrbInitialContextFactory \
-Djava.naming.provider.url=$PROVIDER_URL \
-cp $CLI_CLASSPATH $DEBUG_OPT \
BasicExample UserTransaction
