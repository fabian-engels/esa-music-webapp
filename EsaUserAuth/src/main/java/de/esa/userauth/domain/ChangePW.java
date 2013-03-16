package de.esa.userauth.domain;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 
 * @author martin, fabian
 */
@MessageDriven(mappedName = "JMSTopic", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "JMSTopic")
})
public class ChangePW implements MessageListener {

    @PersistenceContext(name = "persUnit")
    private EntityManager em;
    
    private static final Logger LOGGER = Logger.getLogger(ChangePW.class.getName());
    
    private UserObject getUserByName(final String username) {
        LOGGER.log(Level.INFO, "Username: {0}", username);
        final List<UserObject> resultList = em.createQuery("Select a From UserObject a", UserObject.class).getResultList();

        for (UserObject next : resultList) {
            if (next.getName().equals(username)) {
                return next;
            }
        }
        return null;
    }
    
    @Override
    public void onMessage(Message message) {
        if (!(message instanceof TextMessage)) {
            throw new IllegalArgumentException(
                    "TOPIC Message is not of class " + TextMessage.class.getCanonicalName() + ": " + message);
        }
        final TextMessage msg = (TextMessage) message;
        try {
            final String messageText = msg.getText();
            LOGGER.log(Level.INFO, " TOPIC received: {0}", messageText);
            
            if (messageText.matches("changepw:.+?:.+?")) {
                LOGGER.log(Level.INFO, "ATTEMPTING TO CHANGE PASSWORD");
                final String[] mesArr = messageText.split(":");
                final UserObject user = getUserByName(mesArr[1]);

                user.setPassword(mesArr[2]);
                LOGGER.log(Level.INFO, "PASSWORD CHANGED");

            } else if (messageText.matches("deleteuser:.+?")) {
                LOGGER.log(Level.INFO, "ATTEMPTING TO DELETRE USER");
                final String[] mesArr = messageText.split(":");
                final UserObject user = getUserByName(mesArr[1]);

                em.remove(user);
                LOGGER.log(Level.INFO, "USER DELETED");
            }
          //  Thread.sleep(1000); //Don't do such waiting in production!
            LOGGER.log(Level.INFO, " TOPIC processed: {0}", messageText);
        } catch (JMSException ex) {
           // LOGGER.log(Level.WARNING, " TOPIC Failure processing message: " + msg, ex);
        }
    }
}
