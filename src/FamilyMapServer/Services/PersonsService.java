package FamilyMapServer.Services;

import FamilyMapServer.DAOs.PersonDAO;
import FamilyMapServer.Responses.PersonsResponse;

import java.sql.SQLException;

public class PersonsService {

    public PersonsResponse execute(String username) throws SQLException {

        PersonDAO dao = new PersonDAO();
        PersonsResponse resp = new PersonsResponse();
        resp.setPersons(dao.getAllPersons(username));
        return resp;

    }

}
