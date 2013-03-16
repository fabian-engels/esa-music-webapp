package de.esa.musicwebapp.services.userauth;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 * This class is a JMS Client which produces messages for the JMSTopic.
 * Messages are send without modification.
 * It is necessary to create the class instance on startup to avoid runtime exceptions on UI access. 
 * @author martin
 */
@Startup
@Singleton
public class ChangePWClient {
    /**
     * Destination for all JMS messages which must be provided by the application server.
     */
    @Resource(mappedName = "JMSTopic")
    private Topic jMSTopic;
     /**
      * ConnectionFactory for JMS messages producer must be provided by the application server..
      */
    @Resource(mappedName = "JMSTopicFactory")
    private ConnectionFactory jMSTopicFactory;
    
    /**
     * Method for local access to send JMS messages to JMSTopic.
     * @param messageData
     * @return 
     */
    public boolean sendJMSMessage(String messageData) {
        boolean result = true;
        try {
            this.sendJMSMessageToJMSTopic(messageData);
        } catch (JMSException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Cannot send JMSMessage", ex);
            result = false;
        }finally{
            return result;
        }
    }
    
    /**
     * Helper method to encapsulate the message creation.
     * @param session
     * @param messageData
     * @return
     * @throws JMSException 
     */
    private Message createJMSMessageForjMSTopic(Session session, String messageData) throws JMSException {
        TextMessage tm = session.createTextMessage();
        tm.setText(messageData);
        return tm;
    }

    /**
     * Method only used by class to send a text as message dirctly to the class resource JMSTopic.
     * @param messageData
     * @throws JMSException 
     */
    private void sendJMSMessageToJMSTopic(String messageData) throws JMSException {
        Connection connection = null;
        Session session = null;
        try {
            connection = jMSTopicFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(jMSTopic);
            messageProducer.send(createJMSMessageForjMSTopic(session, messageData));
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Cannot close session", e);
                }
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

}
