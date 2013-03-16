package de.esa.userauth.domain;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

/**
 * This class is designed to be created on application startup,
 * this way all methods are already accessable on user http request.
 * @author martin, fabian
 */
@Startup
@Singleton
public class UserAuth implements UserAuthRemote {
    private static final Logger LOGGER = Logger.getLogger(UserAuth.class.getName());

    @PersistenceContext
    private EntityManager em;
    
    public UserAuth() {
        try {
            LOGGER.log(Level.WARNING, "### Getting JNDI Resources ###");
            InitialContext ict = new InitialContext();
            NamingEnumeration children = ict.list("");
            while(children.hasMoreElements()){
                NameClassPair ncPair = (NameClassPair)children.next();
                LOGGER.log(Level.WARNING, ncPair.getName() + " (type ");
                LOGGER.log(Level.WARNING, ncPair.getClassName() + ")");
            }
        } catch (Exception e) {
        }
    }

    @Override
    public UserObject login(final String username, final String password) throws IllegalUsernameException {
         LOGGER.log(Level.INFO, "login: Username: " + username + " Password: " + password);
        final UserObject user = getUserByName(username);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }else{
            throw new IllegalUsernameException("Unknown username or password.");
            /* For more security we do not throw a username or password specific exception. */
        }
    }

    @Override
    public UserObject register(final String username, final String password) throws IllegalUsernameException {
        if(getUserByName(username) != null){
            throw new IllegalUsernameException("Username already in use.");
        }else{
            em.persist(new UserObject(username, password));
            return login(username, password);
        }
    }

    @Override
    public UserObject getUserByName(final String username) {
        LOGGER.log(Level.INFO, "Username: " + username);
        final List<UserObject> resultList = em.createQuery("Select a From UserObject a", UserObject.class).getResultList();

        for (UserObject next : resultList) {
            if (next.getName().equals(username)) {
                return next;
            }
        }
        return null;
    }

    @Override
    public void deleteUser(final UserObject user) {
        // @TODO throw UserNotFoundException
        if (user == null) {
            throw new IllegalArgumentException("user-UserObject-Object is NULL.");
        }

        final String username = user.getName();

        if (user.getId() < 0) {
            throw new IllegalArgumentException("user-Object has an illegal ID (<0)");
        }

        final UserObject userToDelete = em.find(UserObject.class, user.getId());

        em.getTransaction().begin();
        em.remove(userToDelete);
        em.getTransaction().commit();

        em.close();
    }
}
