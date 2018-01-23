package FamilyMapServer.Services;

import FamilyMapServer.DAOs.UserDAO;
import FamilyMapServer.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClearTest {
    @Test
    public void execute() throws Exception {
        User u = new User();
        u.setUserName("sheila");
        UserDAO dao = new UserDAO();
        dao.addUser(u);
        Clear clear = new Clear();
        assertFalse(dao.hasUser("sheila"));
    }

}