/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.esa.musicwebapp.ui;

import de.esa.musicwebapp.service.userauth.ChangePWClient;
import de.esa.userauth.domain.IllegalUsernameException;
import de.esa.userauth.domain.UserAuthRemote;
import de.esa.userauth.domain.UserObject;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
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

    private static Logger LOGGER = Logger.getLogger(LoginViewBean.class.getName());
    @EJB
    private UserAuthRemote userAuth;
    private UserObject currentUser;
    private String usernameInp;
    private String passwordInp;

    public LoginViewBean() {
        try {
            LOGGER.log(Level.WARNING, "### Getting JNDI Resources ###");
            InitialContext ict = new InitialContext();
            NamingEnumeration children = ict.list("");
            while (children.hasMoreElements()) {
                NameClassPair ncPair = (NameClassPair) children.next();
                LOGGER.log(Level.WARNING, ncPair.getName() + " (type ");
                LOGGER.log(Level.WARNING, ncPair.getClassName() + ")");
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
        try {
            currentUser = userAuth.login(this.usernameInp, this.passwordInp);
        } catch (IllegalUsernameException ex) {
            displayFailure(ex.getLocalizedMessage());
        }
        if (currentUser == null) {
            displayFailure("Login failed username or password are unknown.");
            return "login";
        }
        //return "/search";
        return "search?faces-redirect=true";
    }

    public String register() {
        UserObject result = null;
        try {
            result = userAuth.register(this.usernameInp, this.passwordInp);
        } catch (IllegalUsernameException ex) {
            displayFailure(ex.getLocalizedMessage());
        }
        if (result == null) {
            displayFailure("Username is already in use.");
            return "login";
        }
        return "search";
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
        FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failure", message));
    }
}
