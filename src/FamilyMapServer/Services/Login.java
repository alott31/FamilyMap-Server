package FamilyMapServer.Services;

import FamilyMapServer.AuthToken;
import FamilyMapServer.DAOs.AuthTokenDAO;
import FamilyMapServer.DAOs.UserDAO;
import FamilyMapServer.numGenerator;
import FamilyMapServer.Requests.LoginRequest;
import FamilyMapServer.Responses.LoginResponse;
import FamilyMapServer.User;
import com.google.gson.Gson;

import java.sql.SQLException;

public class Login {
    public LoginResponse execute(LoginRequest request) throws SQLException {
        //initialize objects
        Gson gson = new Gson();
        numGenerator id = new numGenerator();
        UserDAO dao_user = new UserDAO();
        AuthTokenDAO dao_token = new AuthTokenDAO();
        LoginResponse response = new LoginResponse();

        //return requested user (and make sure it exists)
        User user = dao_user.getUser(request.getUserName());
        if(user == null) {
            response.setMessage("Username does not exist.");
            return response;
        }

        //check password and create response
        if(user.getPassword().equals(request.getPassword())) {
            String authToken = id.generate(); //create auth token


            response.setToken(authToken);
            response.setUser(user.getUserName());
            response.setPersonID(user.getPersonID());

            //add auth token to database
            AuthToken at = new AuthToken(authToken, user.getUserName());
            dao_token.addAuthToken(at);

        } else {
            response.setMessage("Incorrect password.");
            return response;
        }

        return response;
    }
}
