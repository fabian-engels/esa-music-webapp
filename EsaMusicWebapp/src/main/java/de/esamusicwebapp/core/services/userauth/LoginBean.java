/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.esamusicwebapp.core.services.userauth;

import de.esa.auth.domain.UserObject;
import de.esa.auth.jpa.IllegalNameException;
import de.esa.auth.jpa.UserAuth;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author nto
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;
    private boolean loggedin;
    @EJB(mappedName = "UserAuth")
    UserAuth userAuth;

    public LoginBean() {
        loggedin = false;
    }

    /**
     * Get the value of password
     *
     * @return the value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the value of password
     *
     * @param password new value of password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the value of username
     *
     * @return the value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the value of username
     *
     * @param username new value of username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void displayMessage() {
        FacesContext.getCurrentInstance().addMessage("loginMessage", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sample error message", "PrimeFaces makes no mistakes"));
    }

    /**
     * Login based on username and password then redirect.
     *
     * @return the value of redirection target
     */
    public String login() {
        if (username.isEmpty() || password.isEmpty()) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.INFO, "Required name or password are missing.");
            FacesContext.getCurrentInstance().addMessage("loginMessage", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Empty value", "Username or password are empty."));
            return "index";
        }
        UserObject usrObj = null;
        try {
            usrObj = userAuth.login(this.username, this.password);
        } catch (IllegalNameException ex) {
            FacesContext.getCurrentInstance().addMessage("loginMessage", new FacesMessage(FacesMessage.SEVERITY_ERROR, "IllegalNameException", "Bad name."));
            Logger.getLogger(LoginBean.class.getName()).log(Level.INFO, null, ex);
            Logger.getLogger(LoginBean.class.getName()).log(Level.INFO, ex.getMessage());
        }

        loggedin = checkCredentials(usrObj);

        if (loggedin) {
            return "search";
        } else {
            FacesContext.getCurrentInstance().addMessage("loginMessage", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credentials check failed","Please check username and password."));
            return "index";
        }
    }

    /**
     * Test credentials of current text input values against a user object.
     *
     * @param usrObj
     * @return
     */
    private boolean checkCredentials(UserObject usrObj) {
        return usrObj == null && (usrObj.getName().equals(this.username) && usrObj.getPassword().equals(this.password));
    }

    public String register() {
        UserObject usrObj = null;
        try {
            Logger.getLogger(LoginBean.class.getName()).log(Level.INFO, "Username: {0}", username);
            Logger.getLogger(LoginBean.class.getName()).log(Level.INFO, "Username: {0}", password);
            usrObj = userAuth.register(this.username, this.password);
        } catch (IllegalNameException ex) {
            /* login failed */
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(LoginBean.class.getName()).log(Level.INFO, ex.getMessage());
        }
        if (checkCredentials(usrObj)) {
            /* login successful */
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sample error message", "PrimeFaces makes no mistakes"));
            Logger.getLogger(LoginBean.class.getName()).log(Level.INFO, "Register successful");
        }
        return "index";
    }
}
