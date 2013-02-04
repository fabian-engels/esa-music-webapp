/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.esa.musicwebapp.service.userauth;

import de.esa.userauth.domain.IllegalUsernameException;
import de.esa.userauth.domain.UserAuthRemote;
import de.esa.userauth.domain.UserObject;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author nto
 */
@Stateless
@LocalBean
public class UserAuthClient {
    
    @EJB//(lookup="corbaname:iiop:localhost:3700#UserAuthRemote")
    private UserAuthRemote userAuth;
    
    public UserAuthClient() {
    }

    public boolean login(final String username, final String password) {
        UserObject userObject = null;
        try {
            userObject = userAuth.login(username, password);
        } catch (IllegalUsernameException ex) {
            // @TODO display JSF message
        } finally {
            return userObject != null;
        }
    }
    
    public UserObject getUserByName(final String username) {
        return userAuth.getUserByName(username);
    }
    
    public boolean register(final String username, final String password) {
        UserObject userObject = null;
        try{
             userObject = userAuth.register(username, password);
        }catch(IllegalUsernameException ex) {
            // @TODO display JSF message
        }finally{
            return userObject != null;
        }
    }
    
    public void deleteUser(final UserObject user) {
        userAuth.deleteUser(user);
    }
    
}
