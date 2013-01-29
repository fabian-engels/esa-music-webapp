package de.esa.user.auth.mdb;

import de.esa.user.auth.domain.UserObject;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;

import de.esa.user.auth.jpa.UserAuthImpl;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.persistence.PersistenceContext;

@MessageDriven(mappedName = "JMSTopic", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "JMSTopic")
})
public class ChangePW implements MessageListener {

    @EJB
    UserAuthImpl userAuthImpl;
    @PersistenceContext(name = "persUnit")
    private EntityManager em;

    @Override
    public void onMessage(Message message) {
        if (!(message instanceof TextMessage)) {
            throw new IllegalArgumentException(
                    "TOPIC Message is not of class " + TextMessage.class.getCanonicalName() + ": " + message);
        }
        final TextMessage msg = (TextMessage) message;
        final Logger logger = Logger.getLogger(ChangePW.class.getName());
        try {
            final String messageText = msg.getText();

            if (messageText.matches("changepw:.+?:.+?")) {
                logger.log(Level.INFO, "ATTEMPTING TO CHANGE PASSWORD");
                final String[] mesArr = messageText.split(":");

                final UserObject user = userAuthImpl.getUserByName(mesArr[1]);

                em.getTransaction().begin();
                user.setPassword(mesArr[2]);
                em.getTransaction().commit();

                logger.log(Level.INFO, "PASSWORD CHANGED");

            } else if (messageText.matches("deleteuser:.+?")) {
                logger.log(Level.INFO, "ATTEMPTING TO DELETRE USER");
                final String[] mesArr = messageText.split(":");

                final UserObject user = userAuthImpl.getUserByName(mesArr[1]);

                em.getTransaction().begin();
                em.remove(user);
                em.getTransaction().commit();
                logger.log(Level.INFO, "USER DELETED");
            }

            logger.log(Level.INFO, " TOPIC received: " + messageText);
            Thread.sleep(1000); //Don't do such waiting in production!
            logger.log(Level.INFO, " TOPIC processed: " + messageText);
        } catch (Exception ex) {
            logger.log(Level.WARNING, " TOPIC Failure processing message: " + msg, ex);
        }
    }
}
