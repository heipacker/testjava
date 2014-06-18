/*
 * @(#) DatabaseHelper.java
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
 * $Id: DatabaseHelper.java,v 1.2 2003/12/05 19:03:56 trentshue Exp $
 * --------------------------------------------------------------------------
 */
package test.dlmu.jotm.jdbc;

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
 *
 */
public class DatabaseHelper {

    private String password;
    private TMService jotm;
    private XADataSource xads;
    private String login;
    private static final String USER_TRANSACTION_JNDI_NAME = "UserTransaction";

    /**
     * Constructor for DatabaseHelper.
     */
    public DatabaseHelper(String database) {
        Properties props = new Properties();
        try {
            props.load(DatabaseHelper.class.getResourceAsStream(database + ".properties"));
        } catch (Exception e) {
            System.out.println("no properties file found found for " + database);
            System.exit(1);
        }

        System.out.println("\n" + database + " configuration:");
        props.list(System.out);
        System.out.println("------------------------\n");

        login = props.getProperty("login");
        password = props.getProperty("password");

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

        xads = new StandardXADataSource();
        try {
            ((StandardXADataSource) xads).setDriverName(props.getProperty("driver"));
            ((StandardXADataSource) xads).setUrl(props.getProperty("url"));
            ((StandardXADataSource) xads).setTransactionManager(jotm.getTransactionManager());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public Connection getConnection() throws SQLException {
        XAConnection xaconn = xads.getXAConnection(login, password);
        return xaconn.getConnection();
    }
    /**
     * Method stop.
     */
    public void stop() {
    	xads = null;
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

