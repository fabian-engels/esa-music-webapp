package de.esa.auth.jpa;

import de.esa.auth.domain.UserObject;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

@Stateless(mappedName= "UserAuth")
public class UserAuthImpl implements UserAuth {

    @Override
    public UserObject login(String name, String password) throws IllegalNameException {
        if (name == null || password == null || name.equals("") || password.equals("")) {
            throw new IllegalNameException("Enter a valid username and password.");
        }

        final UserObject user = getUserByName(name);

        if (user == null) {
            return null;
        }

        if (user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public UserObject register(String name, String password) throws IllegalNameException {
        if (name == null || password == null || name.equals("") || password.equals("")) {
            throw new IllegalNameException("Enter a valid username and password.");
        }

        final UserObject user = getUserByName(name);
        if (user != null) {
            System.out.println("USERNAME ALREADY TAKEN");
            throw new IllegalNameException("The username is already taken.");
        }
        EntityManager em = JPAUtil.getManager();

        em.getTransaction().begin();
        em.persist(new UserObject(name, password));
        em.getTransaction().commit();

        return login(name, password);
    }

    @Override
    public boolean deleteUser(final UserObject user) {
        if (user == null) {
            throw new IllegalArgumentException("user-UserObject-Object is NULL.");
        }

        final String username = user.getName();

        if (user.getId() < 0) {
            throw new IllegalArgumentException("user-Object has an illegal ID (<0)");
        }

        final EntityManager manager = JPAUtil.getManager();

        final UserObject userToDelete = manager.find(UserObject.class, user.getId());

        manager.getTransaction().begin();
        manager.remove(userToDelete);
        manager.getTransaction().commit();

        if (getUserByName(username) == null) {
            return false;
        }
        JPAUtil.shutdown();

        return true;
    }

    @Override
    public UserObject edit(final UserObject user) {


        return null;
    }

    public static UserObject getUserByName(final String name) {

        final List<UserObject> resultList = JPAUtil.getManager().createQuery("Select a From UserObject a", UserObject.class).getResultList();
        //		JPAUtil.shutdown();

        for (UserObject next : resultList) {
            if (next.getName().equals(name)) {
                return next;
            }
        }
        return null;
    }
}
