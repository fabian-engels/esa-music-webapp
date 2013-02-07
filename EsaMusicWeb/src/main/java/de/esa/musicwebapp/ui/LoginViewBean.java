/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author nto
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

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public UserObject getCurrentUser() {
        return this.currentUser;
    }

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

    public String register() {
        if (!verifyCredentials()) {
            return "";
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

    private void displayFailure(String message) {
        displayError("growl", "LoginFailure", message);
    }

    private void displayError(String component, String title, String message) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(FacesMessage.SEVERITY_ERROR, title, message));
    }
}
