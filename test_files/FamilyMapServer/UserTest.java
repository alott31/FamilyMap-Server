package FamilyMapServer;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    //the set test asserts BOTH setters and getters in one test method

    @Test
    public void setUserName() throws Exception {
        User user = new User();
        user.setUserName("sheila");
        assertEquals("sheila", user.getUserName());
    }

    @Test
    public void setPassword() throws Exception {
        User user = new User();
        user.setPassword("password");
        assertEquals("password", user.getPassword());

    }

    @Test
    public void setEmail() throws Exception {
        User user = new User();
        user.setEmail("sheila@gmail.com");
        assertEquals("sheila@gmail.com", user.getEmail());
    }

    @Test
    public void setFirstName() throws Exception {
        User user = new User();
        user.setFirstName("Sheila");
        assertEquals("Sheila", user.getFirstName());
    }

    @Test
    public void setLastName() throws Exception {
        User user = new User();
        user.setLastName("Parker");
        assertEquals("Parker", user.getLastName());
    }

    @Test
    public void setGender() throws Exception {
        User user = new User();
        user.setPassword("f");
        assertEquals("f", user.getGender());
    }

    @Test
    public void setPersonID() throws Exception {
        User user = new User();
        user.setPersonID("12345");
        assertEquals("12345", user.getPersonID());
    }

}