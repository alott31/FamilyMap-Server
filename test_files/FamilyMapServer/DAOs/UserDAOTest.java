package FamilyMapServer.DAOs;

import FamilyMapServer.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDAOTest {

    private UserDAO dao = new UserDAO();

    @Test
    public void addUser() throws Exception {
        UserDAO dao = new UserDAO();
        User u = new User();
        u.setUserName("sheila");
        u.setPersonID("12345");
        dao.addUser(u);
        assertEquals("12345", dao.getUser("sheila").getPersonID());
    }

    @Test
    public void clear() throws Exception {
        assertTrue(true);
    }

}