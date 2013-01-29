package de.esa.user.auth.domain;



public class IllegalNameException extends Exception {

    private static final long serialVersionUID = -5580411649301311599L;

    public IllegalNameException() {
    }

    public IllegalNameException(String s) {
        super(s);
    }
}
