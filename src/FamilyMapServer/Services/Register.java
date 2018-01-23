package FamilyMapServer.Services;

import FamilyMapServer.*;
import FamilyMapServer.DAOs.AuthTokenDAO;
import FamilyMapServer.DAOs.PersonDAO;
import FamilyMapServer.DAOs.UserDAO;
import FamilyMapServer.Requests.RegisterRequest;
import FamilyMapServer.Responses.RegisterResponse;
import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class Register {

    public RegisterResponse execute(RegisterRequest request) throws SQLException {

        //initialize objects
        Gson gson = new Gson();
        numGenerator id = new numGenerator();
        UserDAO dao_user = new UserDAO();
        AuthTokenDAO dao_token = new AuthTokenDAO();
        PersonDAO dao_person = new PersonDAO();
        RegisterResponse response = new RegisterResponse();
        Set<String> prevUsers = new HashSet<>();

        //check if username already exists
        if(dao_user.hasUser(request.getUserName())) {
            response.setMessage("Username already exists, please choose another.");
            return response;
        }

        //generate random numbers for id and token
        String personID = id.generate();
        String authToken = id.generate();

        //create USER and PERSON and AUTH TOKEN, add to database
        String reqData = gson.toJson(request);
        User user = gson.fromJson(reqData, User.class);
        Person person = gson.fromJson(reqData, Person.class);
        user.setPersonID(personID);
        person.setPersonID(personID);
        AuthToken token = new AuthToken(authToken, user.getUserName());
        dao_user.addUser(user);
        dao_token.addAuthToken(token);

        //create 4 generations of persons and events
        CreateFamily generator = new CreateFamily(user.getUserName());
        generator.execute(person, 4);

        //create response
        response.setUser(user.getUserName());
        response.setPersonID(personID);
        response.setToken(authToken);

        return response;
    }
}
