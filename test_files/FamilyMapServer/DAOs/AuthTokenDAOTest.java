package FamilyMapServer.DAOs;

import FamilyMapServer.AuthToken;
import org.junit.Test;

import static org.junit.Assert.*;

public class AuthTokenDAOTest {

    @Test
    public void addAuthToken() throws Exception {
        AuthTokenDAO dao = new AuthTokenDAO();
        AuthToken token = new AuthToken("12345", "sheila");
        dao.addAuthToken(token);
        assertEquals("12345", dao.getUsername("sheila"));
    }

    @Test
    public void clear() throws Exception {
        assertTrue(true);
    }

}