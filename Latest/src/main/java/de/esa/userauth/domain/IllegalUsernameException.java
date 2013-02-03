package de.esa.userauth.domain;

import javax.ejb.EJBException;



public class IllegalUsernameException extends EJBException {

    private static final long serialVersionUID = -5580411649301311599L;

    public IllegalUsernameException() {
    }

    public IllegalUsernameException(String s) {
        super(s);
    }
}
