package FamilyMapServer;

import FamilyMapServer.DAOs.EventDAO;
import FamilyMapServer.DAOs.PersonDAO;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class CreateFamily {

    private String username; // descendant
    private int year; //stores the birth year of a male child for calculating ancestor dates
    private int momYear; //stores the birth year of a female child for calculating ancestor dates
    private ArrayList<Person> persons; //stores all persons that are created this instance
    private ArrayList<Event> events; //stores all events that are created this instance
    private Event marriage; //stores the marriage to ensure husband and wife have corresponding data
    private int eventCount; //counts the events that are created
    private int personCount; //counts the persons that are created

    public CreateFamily(String username) {
        this.username = username;
        events = new ArrayList<Event>();
        persons = new ArrayList<Person>();
        eventCount = 0;
        personCount = 0;
    }

    public int getEventCount() {
        return eventCount;
    }

    public int getPersonCount() {
        return personCount;
    }

    public void execute(Person root, int generations) throws SQLException {
        ArrayList<Person> gen = new ArrayList<Person>();
        setRootEvents(root);
        root.setDescendant(username);
        persons.add(root);
        gen.add(root);

        while(generations > 0) { //iterates through generations
            ArrayList<Person> nextGen = new ArrayList<Person>();
            for (int i = 0; i < gen.size(); i++) { //iterates through each member of the generation
                Person child = gen.get(i);
                Person dad = addDad(child);
                child.setFather(dad.getPersonID());
                Person mom = addMom(dad);
                child.setMother(mom.getPersonID());
                dad.setSpouse(mom.getPersonID());
                mom.setSpouse(dad.getPersonID());
                nextGen.add(dad);
                nextGen.add(mom);
            }

            generations--;
            gen = nextGen;

            //adds new generation to ArrayList of total new persons
            for(int j = 0; j < gen.size(); j++) {
                persons.add(gen.get(j));
            }

        }
        addToDatabase();
    }

    //adds all persons and events to the database
    private void addToDatabase() throws SQLException {

        ArrayList<Event> events = this.events;

        PersonDAO dao_person = new PersonDAO();
        EventDAO dao_event = new EventDAO();
        for(int i = 0; i < persons.size(); i++) {
            Person person = persons.get(i);
            dao_person.addPerson(person);
            personCount++;
        }
        for(int j = 0; j < events.size(); j++) {
            Event event = events.get(j);
            dao_event.addEvent(event);
            eventCount++;
        }
    }

    private void setRootEvents(Person root) {
        String personID = root.getPersonID();
        Random rand = new Random();

        //create birth
        int birthYear = rand.nextInt(30) + 1980;
        year = birthYear;
        momYear = birthYear;
        Event birth = createEvent(personID, "Birth", birthYear);
        events.add(birth);

        //create baptism (could be a one year old -- Catholic)
        int baptismYear = rand.nextInt(10) + birthYear + 1;
        Event baptism = createEvent(personID, "Baptism", baptismYear);
        events.add(baptism);

        //maybe create marriage if they are older than 22
        if (2017 - birthYear > 22) {
            if (rand.nextInt(1) == 1) {
                int marriageYear = rand.nextInt(4) - 2017;
                Event marriage = createEvent(personID, "Marriage", marriageYear);
                events.add(marriage);
            }
        }
    }

    //creates the father of a child
    private Person addDad(Person child) {
        Random rand = new Random();
        numGenerator id = new numGenerator();
        Person dad = new Person();
        String dadID = id.generate();
        String lastName;

        if(child.getGender().equals("m")) {
            lastName = child.getLastName();
        } else {
            lastName = lastName();
            year = momYear;
        }

        dad.setDescendant(username);
        dad.setFirstName(maleName());
        dad.setLastName(lastName);
        dad.setGender("m");
        dad.setPersonID(dadID);

        //create birth
        int hb = rand.nextInt(20);
        hb = year - hb - 20;
        year = hb;
        Event husbandBirth = createEvent(dadID, "Birth", hb);
        events.add(husbandBirth);

        //create baptism
        int hbp = rand.nextInt(10) + hb + 1;
        Event husbandBaptism = createEvent(dadID, "Baptism", hbp);
        events.add(husbandBaptism);

        //create marriage
        int hm = rand.nextInt(10) + hb + 20;
        Event husbandMarriage = createEvent(dadID, "Marriage", hm);
        events.add(husbandMarriage);
        marriage = husbandMarriage;

        //maybe create death, depending on how old the person is
        int hd = rand.nextInt(60) + hm + 5;
        if (2017 - hb > 80 && 2017 - hb < 90) {
            if(rand.nextInt(1) == 1) {
                Event husbandDeath = createEvent(dadID, "Death", hd);
                events.add(husbandDeath);
            }
        } else if (2017 - hb >= 90 ) {
            Event husbandDeath = createEvent(dadID, "Death", hd);
            events.add(husbandDeath);
        }

        return dad;
    }

    //creates the mother of a child
    private Person addMom(Person husband) {
        Random rand = new Random();
        numGenerator id = new numGenerator();
        Person mom = new Person();
        String momID = id.generate();
        String lastName = husband.getLastName();


        mom.setDescendant(username);
        mom.setFirstName(femaleName());
        mom.setLastName(lastName);
        mom.setGender("f");
        mom.setPersonID(momID);

        //create birth
        int mb = rand.nextInt(7);
        mb = year - mb - 5;
        momYear = mb;
        Event momBirth = createEvent(momID, "Birth", mb);
        events.add(momBirth);

        //create baptism
        int mbp = rand.nextInt(10) + mb + 1;
        Event momBaptism = createEvent(momID, "Baptism", mbp);
        events.add(momBaptism);

        //create marriage (should be equal to husband's event)
        int mm = rand.nextInt(10) + mb + 20;
        Event momMarraige = marriage;
        momMarraige.setPersonID(momID);
        events.add(momMarraige);

        //maybe create death depending on the age of the person
        int md = rand.nextInt(60) + mm + 5;
        if (2017 - mb > 80 && 2017 - mb < 90) {
            if(rand.nextInt(1) == 1) {
                Event momDeath = createEvent(momID, "Death", md);
                events.add(momDeath);
            }
        } else if (2017 - mb >= 90 ) {
            Event momDeath = createEvent(momID, "Death", md);
            events.add(momDeath);
        }

        return mom;
    }

    private Event createEvent(String personID, String eventType, int year){
        Gson gson = new Gson();
        Event event = new Event();
        numGenerator id = new numGenerator();
        String place = location();

        event = gson.fromJson(place, Event.class);
        event.setPersonID(personID);
        event.setDescendant(username);
        event.setEventID(id.generate());
        event.setEventType(eventType);
        event.setYear(year);

        return event;
    }

    //Returns random female name
    private String femaleName() {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader
                    ("/Users/Spencer/Documents/School/C S 240/FamilyMapServer [final]/data/fnames.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray names = (JSONArray) jsonObject.get("data");

            Random rand = new Random();
            int n = rand.nextInt(names.size());

            return names.get(n).toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Returns random male name
    private String maleName() {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader
                    ("/Users/Spencer/Documents/School/C S 240/FamilyMapServer [final]/data/mnames.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray names = (JSONArray) jsonObject.get("data");

            Random rand = new Random();
            int n = rand.nextInt(names.size());

            return names.get(n).toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Returns random last name
    private String lastName() {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader
                    ("/Users/Spencer/Documents/School/C S 240/FamilyMapServer [final]/data/snames.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray names = (JSONArray) jsonObject.get("data");

            Random rand = new Random();
            int n = rand.nextInt(names.size());

            return names.get(n).toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Returns random location
    private String location() {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader
                    ("/Users/Spencer/Documents/School/C S 240/FamilyMapServer [final]/data/locations.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray locations = (JSONArray) jsonObject.get("data");

            Random rand = new Random();
            int n = rand.nextInt(locations.size());

            return locations.get(n).toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
