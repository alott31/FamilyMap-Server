package FamilyMapServer.Responses;

import FamilyMapServer.Person;
import java.util.ArrayList;

public class PersonsResponse {

    private ArrayList<Person> persons;
    private String message;

    public PersonsResponse() {
        persons = new ArrayList<Person>();
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public void setMessage(String message) { this.message = message; }
}