/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unico.soap;

import com.unico.common.Gcd;
import com.unico.jms.JMSOperation;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author S.Shah
 */
@Stateless
@WebService
public class OperationJMSSoap implements OperationJMSSoapLocal {

    @EJB
    private JMSOperation jMSOperation;

    @Resource(mappedName = "java:jboss/exported/jms/queue/gcdRestQueue")
    private Queue myQueue;

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory myQueueFactory;

    @Resource(mappedName = "java:jboss/exported/jms/queue/gcdSoapQueue")
    private Queue gcdQueue;

    @Override
    @WebMethod
    public int gcd() throws Exception {
        Message message = jMSOperation.readMessage(myQueue, myQueueFactory, 1);
        if (message != null) {
            Gcd gcd = (Gcd) ((ObjectMessage) message).getObject();
            int gcdvalue = findGreatestCommonDivisor(gcd.getI1(), gcd.getI2());
            jMSOperation.sendMsg(gcdvalue, gcdQueue, myQueueFactory);
            return gcdvalue;
        }
        return 0;
    }

    @Override
    @WebMethod
    public List<Integer> gcdList() throws Exception {
        List<Integer> result = new ArrayList();
        for (Message message : jMSOperation.readAllMsg(gcdQueue, myQueueFactory, 2)) {
            ObjectMessage objectMessage = (ObjectMessage) message;
            result.add((Integer) objectMessage.getObject());
        }
        return result;
    }

    @Override
    @WebMethod
    public int gcdSum() throws Exception {
        int sum = 0;

        for (Message message : jMSOperation.readAllMsg(gcdQueue, myQueueFactory, 2)) {
            ObjectMessage objectMessage = (ObjectMessage) message;
            sum += (Integer) objectMessage.getObject();
        }

        return sum;
    }

    private int findGreatestCommonDivisor(int i1, int i2) {
        if (i2 == 0) {
            return i1;
        }
        return findGreatestCommonDivisor(i2, i1 % i2);
    }

}
