package FamilyMapServer.DAOs;

import FamilyMapServer.Person;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersonDAOTest {
    @Test
    public void addEvent() throws Exception {
        PersonDAO dao = new PersonDAO();
        Person p = new Person();
        p.setDescendant("sheila");
        p.setPersonID("12345");
        dao.addPerson(p);
        assertEquals("sheila", dao.getPerson("12345").getDescendant());
    }

    @Test
    public void delete() throws Exception {
        PersonDAO dao = new PersonDAO();
        Person p = new Person();
        p.setDescendant("sheila");
        p.setPersonID("12345");
        dao.addPerson(p);
        dao.delete("sheila");
        assertFalse(dao.hasPerson("12345"));
    }

    @Test
    public void clear() throws Exception {
        assertTrue(true);
    }

}