package de.esa.userauth.domain;

import javax.ejb.Remote;

/**
 * This interface shall be included in client and server side code,
 * to access the remote methods to manage UserObjects.
 * @author martin, fabian
 */
@Remote
public interface UserAuthRemote {

    UserObject login(final String username, final String password) throws IllegalUsernameException;

    UserObject register(final String username, final String password) throws IllegalUsernameException;

    UserObject getUserByName(final String username);

    void deleteUser(final UserObject user);
    
}
