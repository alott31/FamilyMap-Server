package FamilyMapServer;

import org.junit.Test;

import static org.junit.Assert.*;

public class PersonTest {

    //the set test asserts BOTH setters and getters in one test method

    @Test
    public void setPersonID() throws Exception {
        Person p = new Person();
        p.setPersonID("12345");
        assertEquals("12345", p.getPersonID());
    }

    @Test
    public void setDescendant() throws Exception {
        Person p = new Person();
        p.setDescendant("sheila");
        assertEquals("sheila", p.getDescendant());
    }

    @Test
    public void setFirstName() throws Exception {
        Person p = new Person();
        p.setFirstName("Bob");
        assertEquals("Bob", p.getFirstName());
    }

    @Test
    public void setLastName() throws Exception {
        Person p = new Person();
        p.setLastName("Foo");
        assertEquals("Foo", p.getLastName());
    }

    @Test
    public void setGender() throws Exception {
        Person p = new Person();
        p.setGender("m");
        assertEquals("m", p.getGender());
    }

    @Test
    public void setFather() throws Exception {
        Person p = new Person();
        p.setFather("qwerty");
        assertEquals("qwerty", p.getFather());
    }

    @Test
    public void setMother() throws Exception {
        Person p = new Person();
        p.setMother("blah");
        assertEquals("blah", p.getMother());
    }

    @Test
    public void setSpouse() throws Exception {
        Person p = new Person();
        p.setSpouse("12345");
        assertEquals("12345", p.getSpouse());
    }

}