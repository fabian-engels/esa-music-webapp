/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.esamusicwebapp;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author nto
 */
@Singleton
@LocalBean
public class SessionManagerBean implements HttpSessionListener{
    private static int counter;
    
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        counter++;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        counter--;
    }
    
    public int getActiveSessionsCount(){
        return counter;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

}
