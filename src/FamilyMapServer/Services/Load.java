package FamilyMapServer.Services;

import FamilyMapServer.DAOs.EventDAO;
import FamilyMapServer.DAOs.PersonDAO;
import FamilyMapServer.DAOs.UserDAO;
import FamilyMapServer.Event;
import FamilyMapServer.Person;
import FamilyMapServer.Requests.LoadRequest;
import FamilyMapServer.Responses.LoadResponse;
import FamilyMapServer.User;

import java.sql.SQLException;

public class Load {

    private int userCount;
    private int personCount;
    private int eventCount;

    public LoadResponse execute(LoadRequest request) throws SQLException {

        //create DAOs
        UserDAO dao_user = new UserDAO();
        PersonDAO dao_person = new PersonDAO();
        EventDAO dao_event = new EventDAO();

        //clear all data
        Clear clear = new Clear();
        clear.execute();

        //create arrays and insert data from request body
        User[] users = request.getUsers();
        Person[] persons = request.getPersons();
        Event[] events = request.getEvents();

        //add array data to database
        for (int i = 0; i < users.length; i++) {
            dao_user.addUser(users[i]);
            userCount++;
        }
        for (int i = 0; i < persons.length; i++) {
            dao_person.addPerson(persons[i]);
            personCount++;
        }
        for (int i = 0; i < events.length; i++) {
            dao_event.addEvent(events[i]);
            eventCount++;
        }

        //response
        LoadResponse resp = new LoadResponse();
        resp.setMessage("Successfully added " + userCount + " users, " + personCount + " persons, and " + eventCount + " events to the database.");
        return resp;
    }
}
