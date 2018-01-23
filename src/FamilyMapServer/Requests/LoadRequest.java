package FamilyMapServer.Requests;

import FamilyMapServer.Event;
import FamilyMapServer.Person;
import FamilyMapServer.User;

public class LoadRequest {

    private User[] users;
    private Person[] persons;
    private Event[] events;

    public User[] getUsers() {
        return users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public Event[] getEvents() {
        return events;
    }
}