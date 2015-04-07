package com.unico.jms;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Singleton;
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
@Singleton
public class JMSOperation {

    /**
     *
     * @param type
     */
    public <E extends Serializable> void sendMsg(E message, Queue myQueue, ConnectionFactory myQueueFactory) throws Exception {
        Connection connection = null;
        Session session = null;
        try {
            connection = myQueueFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer publisher = session.createProducer(myQueue);
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
            Destination dest = myQueue;
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
            Destination dest = myQueue;
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
