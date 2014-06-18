#
# JDBC example
#
# $Id$ 

Scenario:
---------
The JDBC is a simple example showing how
to use JTA transactions with JDBC.

Setup:
------

o JOTM is the Transaction Manager, including the registry.
o A database, for example:
	- MySQL
	- PostgreSQL

o DatabaseHelper setups the JDBC objects (i.e., the JDBC Connection) with Enhydra wrappers and 
  associates them to JOTM as their transaction manager.

o JdbcExample then will ask the DatabaseHelper for a connection, update a table within a transaction 
   and complete this transaction thanks to arguments given by the user.

Database Setup:
---------------

The example expects:
o a database named javatest
o a user of login "mojo" and password "jojo"
o a *transactional* table named testdata which is like 

+----+-------+
| ID | FOO   |
+----+-------+
|  1 | 1     |
+----+-------+
	
	o id being an int (primary key)
	o foo being an int
	
Database configuration are stored in properties file (mysql.properties / postgresql.properties) 
which contains the following properties:
o driver - Name of the JDBC driver
o url - URL to connect to the data base
o login - user login
o password - user password 

Example with MySQL:
------------------
# mysql -u root
mysql> grant all privileges on *.* to 'mojo'@'localhost' identified by 'jojo' with grant option;
mysql> grant all privileges on *.* to 'mojo'@'%' identified by 'jojo' with grant option;
mysql> exit

$ mysql -u mojo -p
mysql> create database javatest;
mysql> use javatest;
mysql> create table testdata (id int not null auto_increment primary key,foo int)type=InnoDB;
mysql> insert into testdata values(null, 1);
mysql> exit

You must install the Driver in $JOTM_HOME/lib:
$JOTM_HOME/lib/mysql-connector-java-5.1.6-bin.jar

Example with postgresql:
-----------------------
su - postgres
createuser -P mojo
Enter password for new role: jojo
Enter it again: jojo

Shall the new role be a superuser? (y/n) n
Shall the new role be allowed to create databases? (y/n) y
Shall the new role be allowed to create more new roles? (y/n) y

createdb -O mojo javatest

psql -U mojo javatest
  create table testdata(id integer primary key, foo integer);
  insert into testdata values(1, 1);
  \q

You must install the Driver in $JOTM_HOME/lib:
$JOTM_HOME/lib/postgresql-8.0-310.jdbc3.jar

Compilation:
------------

Set JOTM_HOME to the directory of your JOTM distribution

In the  JOTM_HOME/examples/jdbc directory type:

     ant compile

to compile the JDBC example

Running the JDBC example: (example with the jrmp protocol)
----------------------------------------------------------
Configure the carol.properties: ($JOTM_HOME/conf/carol.properties)

  carol.protocols=jrmp or iiop
  carol.start.ns=true or false

Start the jotm:
- adapt $JOTM_HOME/bin/jotm depending on the carol options.
- start the jotm:
$ jotm &


Run the Client test:
-------------------
Adapt the script run-example.sh, mainly to choose the database driver.
Run it:
$ run-example.sh
