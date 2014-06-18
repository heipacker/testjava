#
#
# JDBC Distributive example
#
#
# $Id$ 

Scenario:
---------
The JDBC Distributive example is a simple example showing how
to use JTA transactions with two JDBC databases.

Setup:
------
o JOTM is the Transaction Manager, including the registry.
o A database, for example:
	- MySQL
	- PostgreSQL
    - Oracle

o DistDatabaseHelper setups the JDBC objects (i.e., the JDBC Connections) with Enhydra
  wrappers and associates them to JOTM as their transaction manager.

o JdbcDistExample then will ask the DistDatabaseHelper for two connections, update a table
  in two different databases within a transaction and complete this transaction thanks to
  arguments given by the user.

Database Setup:
---------------
See ../jdbc/README.txt

Compilation:
------------

Set JOTM_HOME to the directory of your JOTM distribution

In the  JOTM_HOME/examples/jdbc directory type:

     ant compile

to compile the example

Running the JDBC example: (example with the jrmp protocol)
----------------------------------------------------------
Configure the carol.properties: ($JOTM_HOME/conf/carol.properties)

  carol.protocols=jrmp or iiop
  carol.start.ns=true or false

Start the jotm:
- adapt $JOTM_HOME/bin/jotm depending on the carol options.
- start the jotm:
$ jotm

o Start the example:
-------------------
Adapt the script run-example.sh, mainly to choose which database you want to use.
Run it:
$ run-example.sh

o Of course you can mix databases
