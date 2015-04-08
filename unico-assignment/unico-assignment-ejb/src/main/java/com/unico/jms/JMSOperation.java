package com.unico.jms;

import java.io.Serializable;
import java.util.Collections;
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
import javax.jms.QueueBrowser;
import javax.jms.Session;

/**
 *
 * @author S.Shah
 */
@Singleton
public class JMSOperation {

    /**
     * Message type
     *
     * @param type
     */
    public <E extends Serializable> void sendMessage(E message, Queue myQueue, ConnectionFactory myQueueFactory) throws Exception {
        Connection connection = null;
        Session session = null;
        try {
            connection = getConnection(myQueueFactory);
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer publisher = session.createProducer(myQueue);
            connection.start();
            ObjectMessage objectMessage = session.createObjectMessage(message);
            publisher.send(objectMessage);
        } finally {
            closeConnectionNSession(connection, session);
        }
    }

    /**
     *
     * @param session_acknowledge
     */
    public List<Message> readAllMessages(Queue myQueue, ConnectionFactory myQueueFactory) throws Exception {
        Connection connection = null;
        Session session = null;
        try {
            connection = getConnection(myQueueFactory);
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            QueueBrowser browser = session.createBrowser(myQueue);
            return Collections.list(browser.getEnumeration());

        } finally {
            closeConnectionNSession(connection, session);
        }

    }

    /**
     *
     * @param session_acknowledge
     */
    public Message readMessage(Queue myQueue, ConnectionFactory myQueueFactory) throws Exception {
        Connection connection = null;
        Session session = null;
        try {
            connection = getConnection(myQueueFactory);
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination dest = myQueue;
            MessageConsumer consumer = session.createConsumer(dest);
            connection.start();
            return consumer.receive(1);
        } finally {
            closeConnectionNSession(connection, session);
        }

    }

    private Connection getConnection(ConnectionFactory myQueueFactory) throws JMSException {
        return myQueueFactory.createConnection();
    }

    private void closeConnectionNSession(Connection connection, Session session) throws JMSException {
        if (session != null) {
            session.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

}
