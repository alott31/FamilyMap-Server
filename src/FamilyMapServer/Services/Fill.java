package FamilyMapServer.Services;

import FamilyMapServer.CreateFamily;
import FamilyMapServer.DAOs.EventDAO;
import FamilyMapServer.DAOs.PersonDAO;
import FamilyMapServer.DAOs.UserDAO;
import FamilyMapServer.Person;
import FamilyMapServer.Responses.FillResponse;

import java.sql.SQLException;

public class Fill {
    public FillResponse execute(String username, int generations) throws SQLException {

        FillResponse resp = new FillResponse();

        //create DAOs
        PersonDAO dao_person = new PersonDAO();
        UserDAO dao_user = new UserDAO();
        EventDAO dao_event = new EventDAO();

        //make sure user exists
        if(!dao_user.hasUser(username)) {
            resp.setMessage("Username does not exist.");
            return resp;
        }

        //get user data from username in URI
        String id = dao_user.getUser(username).getPersonID();
        Person person = dao_person.getPerson(id);

        //delete data for specific user
        dao_person.delete(username);
        dao_event.delete(username);

        //create family
        CreateFamily generator = new CreateFamily(username);
        generator.execute(person, generations);


        resp.setMessage("Successfully added " + generator.getPersonCount() + " persons, and " + generator.getEventCount() + " events to the database.");
        return resp;
    }

}
