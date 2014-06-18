#!/bin/sh

# Choose your provider URL.
export PROVIDER_URL=rmi://localhost:1099
#export PROVIDER_URL=iiop://localhost:1196

export CLI_CLASSPATH=\
.:\
$JOTM_HOME/lib/jotm-client.jar:\
$JOTM_HOME/lib/jotm-jms.jar:\
$JOTM_HOME/lib/geronimo-jms_1.1_spec.jar:\
$JOTM_HOME/lib/joram-client.jar:\
$JOTM_HOME/lib/joram-shared.jar:\
$JOTM_HOME/lib/joram-mom.jar:\
$JOTM_HOME/lib/jcup.jar:\
$JOTM_HOME/lib/monolog.jar

#Uncomment this to run in debug mode.
#export DEBUG_OPT="-Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,server=y,address=1100,suspend=y"

java \
-Djava.naming.factory.initial=org.ow2.carol.jndi.spi.MultiOrbInitialContextFactory \
-Djava.naming.provider.url=$PROVIDER_URL \
-Dfr.dyade.aaa.agent.A3CONF_DIR=. \
-cp $CLI_CLASSPATH $DEBUG_OPT \
SimpleJmsXa
