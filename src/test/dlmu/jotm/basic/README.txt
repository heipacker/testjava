#
# Basic Example
#
# $Id$

This very simple example shows how to
setup and use a transaction manager on either RMI/JRMP or RMI/IIOP

It starts a standalone jotm and a basic client using UserTransaction.

The client makes 2 transactions:
- the first one with a commit result
- the second one is rolled back because of the expiration
  of a transaction timeout

Set JOTM_HOME to the directory of your JOTM distribution.
add $JOTM_HOME/bin in your PATH to find the jotm command.

Compilation:
------------
cd $JOTM_HOME/examples/basic
ant compile

Running the example:
--------------------
Configure the carol.properties: ($JOTM_HOME/conf/carol.properties)

  carol.protocols=jrmp or iiop
  carol.start.ns=true or false

Start the jotm:
- adapt $JOTM_HOME/bin/jotm depending on the carol options.
- start the jotm:
$ jotm

Run the Client test:
-------------------
"ant run.jrmp" or "ant run.iiop"

You can alternatively use the run-example.sh script to run
this example.
