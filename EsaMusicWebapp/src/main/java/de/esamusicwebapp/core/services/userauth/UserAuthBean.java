/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.esamusicwebapp.core.services.userauth;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author nto
 */
@Singleton
public class UserAuthBean {
/*
    private UserAuth userAuth;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    Context ctx = null;
    UserAuth foo = null;
    final String name = "java:global/EsaUserAuth/UserAuthImpl!de.esa.auth.jpa.UserAuth";
*/
    public UserAuthBean() {
     /*  try {
            ctx = new InitialContext();
            try {
                userAuth = (UserAuth) ctx.lookup(name);
            } catch (javax.naming.NameAlreadyBoundException ex) {
                ctx.rebind(name, userAuth);
                Logger.getLogger(ViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(ViewController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    public void register(String name, String password) {
      /*  try {
            UserObject register = userAuth.register(name, password);
        } catch (IllegalNameException ex) {
            Logger.getLogger(ViewController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    public void login() {
        /*try {
            UserObject login = userAuth.login("fooba", "foopw");
        } catch (IllegalNameException ex) {
            Logger.getLogger(ViewController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    public void deleteUser(Object usrObj) {
       /* boolean deleteUser = userAuth.deleteUser(usrObj);*/
    }
}
