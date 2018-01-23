package FamilyMapServer;

import org.junit.Test;

import static org.junit.Assert.*;

public class EventTest {

    //the set test asserts BOTH setters and getters in one test method

    @Test
    public void setEventID() throws Exception {
        Event e = new Event();
        e.setEventID("hi");
        assertEquals("hi", e.getEventID());
    }

    @Test
    public void setDescendant() throws Exception {
        Event e = new Event();
        e.setDescendant("sheila");
        assertEquals("sheila", e.getDescendant());
    }

    @Test
    public void setPersonID() throws Exception {
        Event e = new Event();
        e.setPersonID("12345");
        assertEquals("12345", e.getPersonID());
    }

    @Test
    public void setLatitude() throws Exception {
        Event e = new Event();
        e.setLatitude(10.12345);
        assertEquals(10.12345, e.getLatitude());
    }

    @Test
    public void setLongitude() throws Exception {
        Event e = new Event();
        e.setLongitude(10.12345);
        assertEquals(10.12345, e.getLongitude());
    }

    @Test
    public void setCountry() throws Exception {
        Event e = new Event();
        e.setCountry("USA");
        assertEquals("USA", e.getCountry());
    }

    @Test
    public void setCity() throws Exception {
        Event e = new Event();
        e.setCity("Provo");
        assertEquals("Provo", e.getCity());
    }

    @Test
    public void setEventType() throws Exception {
        Event e = new Event();
        e.setEventType("Birth");
        assertEquals("Birth", e.getEventID());
    }

    @Test
    public void setYear() throws Exception {
        Event e = new Event();
        e.setYear(1990);
        assertEquals(1990, e.getYear());
    }

}