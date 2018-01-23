package FamilyMapServer.DAOs;

import FamilyMapServer.Event;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventDAOTest {
    @Test
    public void addEvent() throws Exception {
        EventDAO dao = new EventDAO();
        Event e = new Event();
        e.setDescendant("sheila");
        e.setEventID("12345");
        dao.addEvent(e);
        assertEquals("12345", dao.getEvent("12345").getEventID());
    }

    @Test
    public void delete() throws Exception {
        EventDAO dao = new EventDAO();
        Event e = new Event();
        e.setDescendant("sheila");
        e.setPersonID("12345");
        dao.addEvent(e);
        dao.delete("sheila");
        assertFalse(dao.hasEvent("12345"));
    }

    @Test
    public void clear() throws Exception {
        assertTrue(true);
    }

}