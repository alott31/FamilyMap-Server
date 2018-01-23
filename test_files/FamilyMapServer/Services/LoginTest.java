package FamilyMapServer.Services;

import FamilyMapServer.DAOs.UserDAO;
import FamilyMapServer.Requests.LoginRequest;
import FamilyMapServer.Responses.LoginResponse;
import com.google.gson.Gson;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginTest {
    @Test
    public void execute() throws Exception {
        Gson gson = new Gson();
        Login test = new Login();
        LoginResponse resp = new LoginResponse();
        LoginRequest req = gson.fromJson(data(), LoginRequest.class);
        resp = test.execute(req);
        assertTrue(resp != null);
    }

    private String data() {
        String data =
                "    {\n" +
                "      \"userName\": \"sheila\",\n" +
                "      \"password\": \"parker\"\n" +
                "    }";
        return data;
    }

}