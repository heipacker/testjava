/*
 * @(#) SimpleJmsXa.java
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
 *-----------------------------------------------------------------------------
 * $Id: SimpleJmsXa.java,v 1.3 2003-12-05 20:07:06 trentshue Exp $
 *-----------------------------------------------------------------------------
 */
package test.dlmu.jotm.jms;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.TransactionManager;

import org.ow2.jotm.jms.api.JmsManager;
import org.ow2.jotm.jms.JmsManagerImpl;

/**
 * Created on Mar 4, 2002
 * @author  Christophe Ney - cney@batisseurs.com
 * This class instantiates a Jms Server and start clients
 * to send and receive messages on a queue.
 */
public class SimpleJmsXa {

    public static void main(String[] args) {

        // possible to choose another Jms Provider.
        String jmsAdminClassName = "org.ow2.jotm.jms.JmsAdminForJoram";
        if (args.length > 0) {
            jmsAdminClassName = args[0];
        }
        Class cAdmin = null;
        try {
            cAdmin = Class.forName(jmsAdminClassName);
        } catch (ClassNotFoundException e) {
            System.err.println("Cannot load class " + jmsAdminClassName);
            System.exit(1);
        }

        // get initial context
        Context ctx = null;
        try {
            ctx = new InitialContext();
        } catch (NamingException e) {
            System.err.println("No Initial Context");
            System.exit(1);
        }

        // get transaction manager
        System.out.println("[SimpleJmsXa] lookup the TransactionManager.");
        TransactionManager tm = null;
        try {
            tm = (TransactionManager) ctx.lookup("TransactionManager");
        } catch (NamingException e) {
            System.err.println("TransactionManager not found");
            System.exit(1);
        }
        // get user transaction (just check)
        System.out.println("[SimpleJmsXa] lookup the UserTransaction.");
        try {
            ctx.lookup("UserTransaction");
        } catch (NamingException e) {
            System.err.println("UserTransaction not found");
            System.exit(1);
        }

        // init the JMS Manager : Supplies the TransactionManager
        // that will be used in resources.
        System.out.println("[SimpleJmsXa] start the JMS server.");
        JmsManager jms = JmsManagerImpl.getInstance();
        try {
            // the MOM is collocated in the VM (could be remote)
            boolean collocated = true;
            String url = null;
            jms.init(cAdmin, collocated, url, tm);
            System.out.println("[SimpleJmsXa] JMS server started.");
        } catch (Exception e) {
            System.err.println("Cannot initialize JmsManager:" + e);
            System.exit(1);
        }                

        System.out.println("[SimpleJmsXa] create JMS objects, register them in JOTM and bind them.");
        try {
            jms.createQueue("theQueue");
            System.out.println("[SimpleJmsXa] JMS objects available.");
        } catch (NamingException e) {
            System.err.println("Cannot rebind JMS objects:" + e);
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Cannot create Queue:" + e);
            System.exit(1);
        }

        // send a few messages on theQueue (current thread for now)
        System.out.println("[SimpleJmsXa] start simple sender.");
        new SimpleSender().run();

        // receive messages found in theQueue (SimpleReceiver is a MessageListener)
        System.out.println("[SimpleJmsXa] start simple receiver.");
        new SimpleReceiver().run();

        // stop the JMS Manager
        try {
            jms.stop();
            System.out.println("[SimpleJmsXa] JMS server stopped");
        } catch (Exception e) {
            System.err.println("Error while shutting down: " + e);
            System.exit(1);
        }

        // test OK.
        System.exit(0);
    }
}
