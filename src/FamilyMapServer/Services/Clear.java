package FamilyMapServer.Services;

import FamilyMapServer.DAOs.AuthTokenDAO;
import FamilyMapServer.DAOs.EventDAO;
import FamilyMapServer.DAOs.PersonDAO;
import FamilyMapServer.DAOs.UserDAO;
import FamilyMapServer.Responses.ClearResponse;

import java.sql.*;

public class Clear {
    public ClearResponse execute() {
        //create DAOs
        ClearResponse response = new ClearResponse();
        UserDAO dao_user = new UserDAO();
        PersonDAO dao_person = new PersonDAO();
        EventDAO dao_event = new EventDAO();
        AuthTokenDAO dao_token = new AuthTokenDAO();

        //clear all tables
        try {
            dao_user.clear();
            dao_person.clear();
            dao_event.clear();
            dao_token.clear();
            response.setMessage("Clear succeeded.");
        } catch (SQLException e) {
            e.printStackTrace();
            response.setMessage("Clear failed.");
        }
        return response;
    }

}
