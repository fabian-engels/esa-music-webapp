/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.esa.userauth.domain;

import javax.ejb.Remote;

/**
 *
 * @author nto
 */
@Remote
public interface UserAuthRemote {

    UserObject login(final String username, final String password) throws IllegalUsernameException;

    UserObject register(final String username, final String password) throws IllegalUsernameException;

    UserObject getUserByName(final String username);

    void deleteUser(final UserObject user);
    
}
