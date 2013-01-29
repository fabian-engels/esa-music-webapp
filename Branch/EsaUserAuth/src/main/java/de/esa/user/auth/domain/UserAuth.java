package de.esa.user.auth.domain;



import javax.ejb.Remote;

/**
 *
 * @author nto
 */
@Remote
public interface UserAuth {

	public UserObject login(final String name, final String password) throws IllegalNameException;

	public UserObject register(final String name, final String password) throws IllegalNameException;
	
	public boolean deleteUser(final UserObject user);
	
	public UserObject edit(final UserObject user);
}
