/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.esa.musicwebapp.service.userauth;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 *
 * @author nto
 */
@Stateless
@LocalBean
public class ChangePWClient {
    @Resource(mappedName = "JMSTopic")
    private Topic jMSTopic;
    @Resource(mappedName = "JMSTopicFactory")
    private ConnectionFactory jMSTopicFactory;

    
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
    
    private Message createJMSMessageForjMSTopic(Session session, String messageData) throws JMSException {
        // TODO create and populate message to send
        TextMessage tm = session.createTextMessage();
        tm.setText(messageData);
        return tm;
    }

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
