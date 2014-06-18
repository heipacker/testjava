/*
 * @(#) BasicExample.java
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
 * $Id: BasicExample.java,v 1.2 2003/12/05 19:03:56 trentshue Exp $
 * --------------------------------------------------------------------------
 */
package test.dlmu.jotm.basic;

import java.lang.reflect.Field;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

/**
 * 
 * a basic example of transaction.
 * 
 * @author  Christophe Ney -  cney@batisseurs.com
 */
public class BasicExample {

    public static void main(String[] args) {
        if (args.length!=1) {
            System.out.println("usage : BasicExample [userTransactionURL]");
            System.exit(1);
        }


        UserTransaction utc = null;

        try {
            System.out.println("create initial context");
            Context ictx = new InitialContext();
            System.out.println("lookup UserTransaction at : "+args[0]);
            utc = (UserTransaction)ictx.lookup(args[0]);
        } catch (Exception e) {
            System.out.println("Exception of type :"+e.getClass().getName()+" has been thrown");
            System.out.println("Exception message :"+e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        
        System.out.println();

        //a simple transaction
        try {
            System.out.println("a simple transaction which is committed:");
            System.out.println("\t- initial status : "+getStatusName(utc.getStatus()));
            utc.begin();
            System.out.println("\t- after begin status : "+getStatusName(utc.getStatus()));
            utc.commit();
            System.out.println("\t- after commit status : "+getStatusName(utc.getStatus()));            
        } catch (Exception e) {
            System.out.println("Exception of type :"+e.getClass().getName()+" has been thrown");
            System.out.println("Exception message :"+e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println();
        // a transaction with a rollback
        try {
            System.out.println("a simple transaction which is rolled back.");
            System.out.println("we set a transaction timeout to 1 second, begin the transaction, "+ 
                               "and wait 5 seconds before committing it:");
            utc.setTransactionTimeout(1);
            System.out.println("\t- initial status : "+getStatusName(utc.getStatus()));
            utc.begin();
            System.out.println("\t- after begin status : "+getStatusName(utc.getStatus()));
            System.out.println("\t- wait for 5 seconds");
            Thread.sleep(5*1000);
            utc.commit();
            System.out.println("ERROR: the commit method should have failed due to timeout expiration");
            System.exit(1);
        } catch (RollbackException e) {
            try {
                System.out.println("\t- after rollback status : "+ getStatusName(utc.getStatus()));
            } catch (Exception ex) {
                System.out.println("Exception of type :"+e.getClass().getName()+" has been thrown");
                System.out.println("Exception message :"+e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }                
        } catch (Exception e) {
            System.out.println("Exception of type :"+e.getClass().getName()+" has been thrown");
            System.out.println("Exception message :"+e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("\nBasic example is OK.");
    }

    public static String getStatusName(int status)  {
        String statusName = null;
        try {
            Field[] flds = Status.class.getDeclaredFields();
            for (int i=0; i<flds.length; i++) {
                if (flds[i].getInt(null) == status)
                    statusName = flds[i].getName();
            }
        } catch (Exception e) {
            statusName = "invalid status value!";
        }
        return statusName;
    }
}
