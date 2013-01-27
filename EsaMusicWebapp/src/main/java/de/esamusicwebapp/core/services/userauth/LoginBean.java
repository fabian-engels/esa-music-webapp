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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author nto
 */
@ManagedBean
@SessionScoped 
public class LoginBean implements Serializable{

    private String username;
    private String password;
    private boolean loggedin;
    @EJB(mappedName="UserAuth")
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

    /**
     * Login based on username and password then redirect.
     * 
     * @return the value of redirection target
     */
    public String login() {
        if(username.isEmpty()||password.isEmpty()){
            return "index";
        }else{
            UserObject login=null;
            try {
                login = userAuth.login(this.username, this.password);
            } catch (IllegalNameException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.INFO, null, ex);
                Logger.getLogger(LoginBean.class.getName()).log(Level.INFO, ex.getMessage());
            }
            if(login!=null){
                loggedin=true;
            }
        }
        
        if(loggedin){
            return "search";
        }else{
            return "index";
        }
    }
    
    public String register() {
        UserObject register = null;
        try {
            Logger.getLogger(LoginBean.class.getName()).log(Level.INFO, "Username: " + username);
            Logger.getLogger(LoginBean.class.getName()).log(Level.INFO, "Username: " + password);
            register = userAuth.register(this.username, this.password);
        } catch (IllegalNameException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(LoginBean.class.getName()).log(Level.INFO, ex.getMessage());
        }
        if(register!=null) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.INFO, "Register successful");
        }
        return "index";
    }
    
}
