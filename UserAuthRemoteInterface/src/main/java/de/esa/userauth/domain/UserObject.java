package de.esa.userauth.domain;



import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User domain object to be used in business logic as well as in the user interface.
 * The annoations are used by JPA for transparent object persistence.
 *  @author martin, fabian
 */
@Entity
@Table(name="user")
public class UserObject implements Serializable{

	@Id
	@GeneratedValue
	private int id;
	
	private String name;
	
	private String password;
	
	public UserObject() {}
	
	public UserObject(final String name, final String password) {
		this.name = name;
		this.setPassword(password);
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}

}
