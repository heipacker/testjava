/*
 * @(#) SimpleSender.java
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
 * $Id: SimpleSender.java,v 1.3 2003-12-05 20:07:06 trentshue Exp $
 *-----------------------------------------------------------------------------
 */
package test.dlmu.jotm.jms;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;

/**
 * Created on Mar 4, 2002
 * @author  Christophe Ney - cney@batisseurs.com
 */
public class SimpleSender extends Thread {

    /**
     * Send a few messages on a given queue
     */
    void sendMessages(String msg[], QueueConnectionFactory factory, Queue queue) throws Exception {
        // create a connection, session, sender, and the message QueueConnection conn;
        try {
            QueueConnection connection = factory.createQueueConnection();
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            QueueSender sender = session.createSender(queue);

            // start up the connection, send the message
            connection.start();
            for (int i = 0; i < msg.length; i++) {
                TextMessage message = session.createTextMessage(msg[i]);
                sender.send(message);
                System.out.println("[SimpleSender] send : " + message.getText());
            }
            
            // now close all resources
            connection.stop();
            sender.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("[SimpleSender] " + e);
            e.printStackTrace();
            throw e;
        }
    }

    public void run() {
        try {
            // Use JNDI to find the connection factory and the destination
            Context ctx = new InitialContext();
            QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup("QCF");
            Queue queue = (Queue) ctx.lookup("theQueue");
            UserTransaction utx = (UserTransaction)ctx.lookup("UserTransaction");

            sendMessages(new String[]{"non transactional message"}, factory, queue);

            utx.begin();
            sendMessages(new String[]{"transactional message with commit"}, factory, queue);
            utx.commit();

            utx.begin();
            sendMessages(new String[]{"transactional message with rollback"}, factory, queue);
            utx.rollback();

            sendMessages(new String[]{"LAST message"}, factory, queue);

        } catch (Exception e) {
            System.err.println("Exception thrown from SimpleSender " + e);
            System.exit(1);
        }
    }


}
