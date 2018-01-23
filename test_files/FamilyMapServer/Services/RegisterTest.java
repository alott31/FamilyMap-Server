package FamilyMapServer.Services;

import FamilyMapServer.DAOs.UserDAO;
import FamilyMapServer.Requests.RegisterRequest;
import com.google.gson.Gson;
import org.junit.Test;

import static org.junit.Assert.*;

public class RegisterTest {
    @Test
    public void execute() throws Exception {
        Gson gson = new Gson();
        RegisterRequest req = gson.fromJson(data(), RegisterRequest.class);
        Register test = new Register();
        test.execute(req);

        UserDAO dao = new UserDAO();
        assertTrue(dao.hasUser("sheila"));
    }

    private String data() {
        String data = "    {\n" +
                "      \"userName\": \"sheila\",\n" +
                "      \"password\": \"parker\",\n" +
                "      \"email\": \"sheila@parker.com\",\n" +
                "      \"firstName\": \"Sheila\",\n" +
                "      \"lastName\": \"Parker\",\n" +
                "      \"gender\": \"f\",\n" +
                "      \"personID\": \"Sheila_Parker\"\n" +
                "    }";
        return data;
    }

}