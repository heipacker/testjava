package test.dlmu.jotm.jdcbdist;

/*
 * @(#) JdbcDistExample.java
 *
 *
 * JOTM: Java Open Transaction Manager 
 *
 *
 * This module was orginally developed by 
 *
 *  - INRIA (www.inria.fr)inside the ObjectWeb Consortium 
 *    (http://www.objectweb.org)
 * 
 * --------------------------------------------------------------------------
 *  The original code and portions created by INRIA are 
 *  Copyright (c) 2002 INRIA  
 *  All rights reserved.
 *  
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 *
 * -Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. 
 *
 * -Redistributions in binary form must reproduce the above copyright notice, 
 * this list of conditions and the following disclaimer in the documentation 
 * and/or other materials provided with the distribution. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * --------------------------------------------------------------------------
 * $Id: JdbcDistExample.java,v 1.1 2004-01-30 23:13:40 tonyortiz Exp $
 * --------------------------------------------------------------------------
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.UserTransaction;

/**
 * @author jmesnil
 * Updated tortiz
 *
 * This class is an example of use of JOTM within a Distributed DataBase
 */
public class JdbcDistExample {

    private static final String USAGE = "usage: java JdbcDistExample [database1 database2] [commit|rollback] [number]";
    private static final String SQL_REQUEST1 = "select id, foo from testdata";
    private static final String SQL_QUERY1 = "update testdata set foo = ? where id=1";
    private static final String SQL_REQUEST2 = "select id, foo from testdata";
    private static final String SQL_QUERY2 = "update testdata set foo = ? where id=1";
    private static final String USER_TRANSACTION_JNDI_NAME = "UserTransaction";
    private static Connection conn1 = null;
    private static Connection conn2 = null;

    private static void printTable(Connection conn, String sqlRequest) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(sqlRequest);
            int numcols = rset.getMetaData().getColumnCount();
            for (int i = 1; i <= numcols; i++) {
                System.out.print("\t" + rset.getMetaData().getColumnName(i));
            }
            System.out.println();
            while (rset.next()) {
                for (int i = 1; i <= numcols; i++) {
                    System.out.print("\t" + rset.getString(i));
                }
                System.out.println("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateTable(Connection conn, String sqlQuery, int newValue) {
        try {           
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setInt(1, newValue);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    	args = new String[]{"mysql1", "mysql2", "commit", "1"};
        if (args.length != 4 || (!args[2].equals("commit") && !args[2].equals("rollback"))) {
            System.out.println(USAGE + "\n");
            System.exit(1);
        }

        String completion = args[2];

        int newValue = 0;
        try {
            newValue = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            System.out.println(USAGE);
            System.out.println("[number] has to be an integer\n");
            System.exit(1);
        }

        System.out.println("start server");
        DistDatabaseHelper ddbHelper = new DistDatabaseHelper(args[0], args[1]);

        UserTransaction utx = null;
        try {
            System.out.println("create initial context");
            Context ictx = new InitialContext();
            System.out.println("lookup UserTransaction at : " + USER_TRANSACTION_JNDI_NAME);
            utx = (UserTransaction) ictx.lookup(USER_TRANSACTION_JNDI_NAME);
        } catch (Exception e) {
            System.out.println("Exception of type :" + e.getClass().getName() + " has been thrown");
            System.out.println("Exception message :" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        try {
            System.out.println("get a connection");
            conn1 = ddbHelper.getConnection1();
            conn2 = ddbHelper.getConnection2();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("before transaction, table is:");
        printTable(conn1, SQL_REQUEST1);
        printTable(conn2, SQL_REQUEST2);

        try {
            System.out.println("begin a transaction");
            utx.begin();

            System.out.println("update the table");
            updateTable(conn1, SQL_QUERY1, newValue);
            updateTable(conn2, SQL_QUERY2, newValue);

            if (completion.equals("commit")) {
                System.out.println("*commit* the transaction");
                utx.commit();
            } else {
                System.out.println("*rollback* the transaction");
                utx.rollback();
            }
        } catch (Exception e) {
            System.out.println("Exception of type :" + e.getClass().getName() + " has been thrown");
            System.out.println("Exception message :" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        utx = null;

        System.out.println("after transaction, table is:");
        printTable(conn1, SQL_REQUEST1);
        printTable(conn2, SQL_REQUEST2);

        try {
            System.out.println("close connection");
            conn1.close();
            conn2.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn1 = null;
            conn2 = null;
        }

        System.out.println("stop server");
        ddbHelper.stop();

        System.out.println("JDBC example is ok.\n");
        System.exit(0);
    }
}
