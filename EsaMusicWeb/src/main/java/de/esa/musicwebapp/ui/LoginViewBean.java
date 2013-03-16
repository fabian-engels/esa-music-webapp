package de.esa.musicwebapp.ui;

import de.esa.userauth.domain.IllegalUsernameException;
import de.esa.userauth.domain.UserAuthRemote;
import de.esa.userauth.domain.UserObject;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;

/**
 * This class is created on application access. The user login state is manged
 * here, therefore it is necessary to
 *
 * @author Fabian
 */
@ManagedBean(name = "login")
@SessionScoped
public class LoginViewBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(LoginViewBean.class.getName());
    @EJB
    private UserAuthRemote userAuth;
    private UserObject currentUser;
    private String usernameInp;
    private String passwordInp;

    public LoginViewBean() {
        /* logger code to visualize all the available JNDI resources of this domain */
        try {
            LOGGER.log(Level.INFO, "### Getting JNDI Resources ###");
            InitialContext ict = new InitialContext();
            NamingEnumeration<NameClassPair> children = ict.list("");
            while (children.hasMoreElements()) {
                NameClassPair ncPair = children.next();
                LOGGER.log(Level.INFO, "{0} (type ", ncPair.getName());
                LOGGER.log(Level.INFO, "{0})", ncPair.getClassName());
            }
        } catch (Exception e) {
        }
    }

    /**
     * Method to determine the login state of the current user.
     * 
     * @return login state of currentUser
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public UserObject getCurrentUser() {
        return this.currentUser;
    }

    /**
     *Method to become redirected to the second view of this webapplication
     * if the user credentials are valid and known in the database.
     * 
     * @return
     */
    public String login() {
        if (!verifyCredentials()) {
            return "";
        }
        try {
            currentUser = userAuth.login(this.usernameInp, this.passwordInp);
        } catch (IllegalUsernameException ex) {
            displayFailure(ex.getLocalizedMessage());
            return "";
        }
        if (currentUser == null) {
            displayFailure("Username or password are unknown.");
            return "";
        } else {
            return "search?faces-redirect=true";
        }
    }

    /**
     * Helper method to validate both input field values and display error
     * notification on the growl component in case of negative evaluation.
     *
     * @return true if logged in
     */
    private boolean verifyCredentials() {
        boolean result = true;
        if (usernameInp == null || usernameInp.trim().length() == 0) {
            displayError("growl", "Invalid input", "Username is required.");
            result = false;
        }
        if (passwordInp == null || passwordInp.trim().length() == 0) {
            displayError("growl", "Invalid input", "Password is required.");
            result = false;
        }
        return result;
    }

    /**
     * Method to register a new user based on the input values of username and password.
     * This method delegates to the userAuth.register() method.
     * 
     * @return empty string to not redirect the navigation
     */
    public String register() {
        if (!verifyCredentials()) {
            return ""; /*supress warning of missing navigation rule*/
        }
        try {
            currentUser = userAuth.register(this.usernameInp, this.passwordInp);
        } catch (IllegalUsernameException ex) {
            displayFailure(ex.getLocalizedMessage());
            return "";
        }
        if (currentUser == null) {
            displayFailure("Username is already in use.");
            return "";
        } else {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registration was successful."));
            return "";
        }
    }

    public String getUsernameInp() {
        return usernameInp;
    }

    public void setUsernameInp(String usernameInp) {
        this.usernameInp = usernameInp;
    }

    public String getPasswordInp() {
        return passwordInp;
    }

    public void setPasswordInp(String passwordInp) {
        this.passwordInp = passwordInp;
    }

    /**
     * Helper method to display failure messages to the predefined growl component.
     * @param message 
     */
    private void displayFailure(String message) {
        displayError("growl", "LoginFailure", message);
    }

    /**
     * Helper method to simplify the display of error messages.
     * @param component
     * @param title
     * @param message 
     */
    private void displayError(String component, String title, String message) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(FacesMessage.SEVERITY_ERROR, title, message));
    }
}
