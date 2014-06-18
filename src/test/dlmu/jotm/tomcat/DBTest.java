/*
 * @(#) DBTest.java
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
 * $Id: DBTest.java,v 1.2 2003/12/05 19:03:56 trentshue Exp $
 * --------------------------------------------------------------------------
 */
 
package test.dlmu.jotm.tomcat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;

public class DBTest{

    int foo = -1;
    // value stored in DB

    public void init(String completion) {
        try{
            Context ctx = new InitialContext();
 
            // JDBC stuff
            DataSource ds =
                (DataSource)ctx.lookup("java:comp/env/jdbc/myDB");

            UserTransaction ut = (UserTransaction)ctx.lookup("java:comp/UserTransaction");

            java.sql.Connection conn = ds.getConnection();

            System.out.println("<<< beginning the transaction >>>");
            ut.begin();

             // JDBC statements
             Statement stmt = conn.createStatement();
             ResultSet rst =
                 stmt.executeQuery("select id, foo from testdata");
             if(rst.next()) {
                 foo=rst.getInt(2);
             }
             System.out.println("foo = "+ foo +" (before completion)");

             PreparedStatement pstmt = conn.prepareStatement("update testdata set foo=? where id=1");
             pstmt.setInt(1,++foo);
             pstmt.executeUpdate();

              if (completion != null && completion.equals("commit")) {
                  System.out.println("<<< committing the transaction >>>");
                  ut.commit();
              } else {
                  System.out.println("<<< rolling back the transaction >>>");
                  ut.rollback();
              }

             // we set foo to the value stored in the DB
             rst =
                 stmt.executeQuery("select id, foo from testdata");
             if(rst.next()) {
                 foo=rst.getInt(2);
             }
             System.out.println("foo = "+ foo +" (after completion)");

             conn.close();
             System.out.println("<<< done >>>");
        }catch(Exception e) {
            System.out.print("DBTest >> ");
            e.printStackTrace();
        }
    }

    public String getFoo() { return ""+foo; }
}

