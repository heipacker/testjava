package test.dlmu.jotm.jdcbdist;

/*
 * @(#) DistDatabaseHelper.java
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
 * $Id: DistDatabaseHelper.java,v 1.1 2004-01-30 23:11:09 tonyortiz Exp $
 * --------------------------------------------------------------------------
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.sql.XAConnection;
import javax.sql.XADataSource;
import org.objectweb.jotm.Jotm;
import org.objectweb.transaction.jta.TMService;

import org.enhydra.jdbc.standard.StandardXADataSource;


/**
 * @author jmesnil
 * updated tortiz
 *
 */
public class DistDatabaseHelper {

    private String password1;
    private String password2;
    private TMService jotm;
    private XADataSource xads1;
    private XADataSource xads2;
    private String login1;
    private String login2;
    private static final String USER_TRANSACTION_JNDI_NAME = "UserTransaction";

    /**
     * Constructor for DistDatabaseHelper.
     */
    public DistDatabaseHelper(String database1, String database2) {
        Properties props1 = new Properties();
        Properties props2 = new Properties();

        try {
            props1.load(DistDatabaseHelper.class.getResourceAsStream(database1 + ".properties"));
        } catch (Exception e) {
            System.out.println("no properties file found found for " + database1);
            System.exit(1);
        }

        System.out.println("\n" + database1 + " configuration:");
        props1.list(System.out);
        System.out.println("------------------------\n");

        login1 = props1.getProperty("login");
        password1 = props1.getProperty("password");

        try {
            props2.load(DistDatabaseHelper.class.getResourceAsStream(database2 + ".properties"));
        } catch (Exception e) {
            System.out.println("no properties file found found for " + database2);
            System.exit(1);
        }

        System.out.println("\n" + database2 + " configuration:");
        props2.list(System.out);
        System.out.println("------------------------\n");

        login2 = props2.getProperty("login");
        password2 = props2.getProperty("password");

        // Get a transction manager       
        try {
        	// creates an instance of JOTM with a local transaction factory which is not bound to a registry
            jotm = new Jotm(true, false);
            InitialContext ictx = new InitialContext();
            ictx.rebind(USER_TRANSACTION_JNDI_NAME, jotm.getUserTransaction());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        xads1 = new StandardXADataSource();
        try {
            ((StandardXADataSource) xads1).setDriverName(props1.getProperty("driver"));
            ((StandardXADataSource) xads1).setUrl(props1.getProperty("url"));
            ((StandardXADataSource) xads1).setTransactionManager(jotm.getTransactionManager());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        xads2 = new StandardXADataSource();
        try {
            ((StandardXADataSource) xads2).setDriverName(props2.getProperty("driver"));
            ((StandardXADataSource) xads2).setUrl(props2.getProperty("url"));
            ((StandardXADataSource) xads2).setTransactionManager(jotm.getTransactionManager());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public Connection getConnection1() throws SQLException {
        XAConnection xaconn = xads1.getXAConnection(login1, password1);
        return xaconn.getConnection();
    }

    public Connection getConnection2() throws SQLException {
        XAConnection xaconn = xads2.getXAConnection(login2, password2);
        return xaconn.getConnection();
    }

    /**
     * Method stop.
     */
    public void stop() {
    	xads1 = null;
        xads2 = null;

        try {
           InitialContext ictx = new InitialContext();
           ictx.unbind(USER_TRANSACTION_JNDI_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }

        jotm.stop();
        jotm = null;
    }

}

