###############
#             #
# JMS example #
#             #
###############

# $Id: README.txt,v 1.11 2005-06-03 21:20:06 rogerperey Exp $

Scenario:
---------

The SimpleJmsXa example is a simple example showing how
to use JTA transactions with a JMS server.

It starts a standalone jotm and a basic client using UserTransaction
and JORAM as the JMS server.

The a3server.xml file is needed for Joram configuration.

The monolog.properties is used only to configure log messages.
See monolog for details.

JmsXA setups the JMS objects (Queue, Session and ConnectionFactory) and
registers them in JOTM as XA resources

SimpleSender sends 4 messages on a JMS queue
  - one is outside a transaction
  - one is inside a transaction with a commit result
  - one is inside a transaction with a rollback result
  - a last one with a special text to stop SimpleReceiver

SimpleReceiver receives 3 messages from this JMS queue
  - the one which was outside a transaction
  - the one which was inside a transaction with a commit result
  - the last one
  (SimpleReceiver does not received the 3rd message which was sent
  because it has been rolled back.)

Set JOTM_HOME to the directory of your JOTM distribution.
add $JOTM_HOME/bin in your PATH to find the jotm command.

Compilation:
------------
cd $JOTM_HOME/examples/jms
ant compile

Run the example:
----------------
Configure the carol.properties: ($JOTM_HOME/conf/carol.properties)

  carol.protocols=jrmp or iiop
  carol.start.ns=true or false

Start the jotm:
- adapt $JOTM_HOME/bin/jotm depending on the carol options.
- start the jotm:
$ jotm


Run the Client test:
-------------------
"ant run.jms"



