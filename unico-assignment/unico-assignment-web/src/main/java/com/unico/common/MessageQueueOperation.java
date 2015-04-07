/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unico.common;

import java.io.Serializable;
import java.util.List;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

/**
 *
 * @author S.Shah
 */
public class MessageQueueOperation {

    private static MessageQueueOperation instance;

    static {
        instance = new MessageQueueOperation();
    }

    private MessageQueueOperation() {
    }

    public static MessageQueueOperation getInstance() {
        return instance;
    }

    public <E extends Serializable> void sendMsg(E message, Queue myQueue, ConnectionFactory myQueueFactory) throws Exception {
        Connection connection = null;
        Session session = null;
        try {
            connection = myQueueFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer publisher = session.createProducer((Destination) myQueue);
            connection.start();
            ObjectMessage objectMessage = session.createObjectMessage(message);
            publisher.send(objectMessage);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *
     * @param session_acknowledge
     */
    public List<Message> readAllMsg(Queue myQueue, ConnectionFactory myQueueFactory, int session_acknowledge) throws Exception {
        Connection connection = null;
        Session session = null;
        try {
            connection = myQueueFactory.createConnection();
            session = connection.createSession(false, session_acknowledge);
            Destination dest = (Destination) myQueue;
            MessageConsumer consumer = session.createConsumer(dest);
            connection.start();
            java.util.ArrayList<Message> messages = new java.util.ArrayList<Message>();
            while (true) {
                Message message = consumer.receive(1);
                if (message != null) {
                    messages.add(message);
                } else {
                    break;
                }
            }
            return messages;

        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     *
     * @param session_acknowledge
     */
    public Message readMessage(Queue myQueue, ConnectionFactory myQueueFactory, int session_acknowledge) throws Exception {
        Connection connection = null;
        Session session = null;
        try {
            connection = myQueueFactory.createConnection();
            session = connection.createSession(false, session_acknowledge);
            Destination dest = (Destination) myQueue;
            MessageConsumer consumer = session.createConsumer(dest);
            connection.start();
            return consumer.receive(1);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
