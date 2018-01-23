package FamilyMapServer.DAOs;

import FamilyMapServer.Person;

import java.sql.*;
import java.util.ArrayList;

public class PersonDAO {
    public void addPerson(Person person) throws SQLException {
        //make connection
        String url = "jdbc:mysql://localhost:3306/FamilyMapDatabase?verifyServerCertificate=false&useSSL=true";
        Connection conn = DriverManager.getConnection(url, "root", "password");
        Statement st = conn.createStatement();

        //execute SQL
        String insert = "insert into persons (person_ID, descendant, first_name, " +
                "last_name, gender, father_ID, mother_ID, spouse_ID) values ('"+
                person.getPersonID() + "', '" +
                person.getDescendant() + "', '" +
                person.getFirstName() + "', '" +
                person.getLastName() + "', '" +
                person.getGender() + "', '" +
                person.getFather() + "', '" +
                person.getMother() + "', '" +
                person.getSpouse() + "') ";

        st.executeUpdate(insert);

        //close connection
        st.close();
        conn.close();
    }

    public Person getPerson(String personID) throws SQLException {
        //make connection
        String url = "jdbc:mysql://localhost:3306/FamilyMapDatabase?verifyServerCertificate=false&useSSL=true";
        Connection conn = DriverManager.getConnection(url, "root", "password");
        Statement st = conn.createStatement();

        //execute SQL
        ResultSet rs = st.executeQuery("SELECT * FROM persons WHERE person_ID = '" + personID + "'");

        while(rs.next()) {
            Person result = new Person();
            result.setDescendant(rs.getString("descendant"));
            result.setFirstName(rs.getString("first_name"));
            result.setLastName(rs.getString("last_name"));
            result.setGender(rs.getString("gender"));
            result.setFather(rs.getString("father_ID"));
            result.setMother(rs.getString("mother_ID"));
            result.setSpouse(rs.getString("spouse_ID"));
            result.setPersonID(rs.getString("person_ID"));

            //close connection
            rs.close();
            st.close();
            conn.close();
            return result;
        }
        rs.close();
        st.close();
        conn.close();
        return null;
    }

    public void delete(String username) throws SQLException {
        //make connection
        String url = "jdbc:mysql://localhost:3306/FamilyMapDatabase?verifyServerCertificate=false&useSSL=true";
        Connection conn = DriverManager.getConnection(url, "root", "password");
        Statement st = conn.createStatement();

        //execute SQL
        st.executeUpdate("DELETE FROM persons WHERE descendant = '" + username + "'");

        //close connection
        st.close();
        conn.close();
    }

    public void clear() throws SQLException {
        //make connection
        String url = "jdbc:mysql://localhost:3306/FamilyMapDatabase?verifyServerCertificate=false&useSSL=true";
        Connection conn = DriverManager.getConnection(url, "root", "password");
        Statement st = conn.createStatement();

        //execute SQL
        st.executeUpdate("truncate persons");

        //close connection
        st.close();
        conn.close();
    }

    public ArrayList<Person> getAllPersons(String username) throws SQLException {
        //make connection
        String url = "jdbc:mysql://localhost:3306/FamilyMapDatabase?verifyServerCertificate=false&useSSL=true";
        Connection conn = DriverManager.getConnection(url, "root", "password");
        Statement st = conn.createStatement();

        //execute SQL
        ResultSet rs = st.executeQuery("SELECT * FROM persons WHERE descendant = '" + username + "'");
        ArrayList<Person> persons = new ArrayList<>();

        while (rs.next()) {
            Person result = new Person();
            result.setDescendant(username);
            result.setFirstName(rs.getString("first_name"));
            result.setLastName(rs.getString("last_name"));
            result.setGender(rs.getString("gender"));
            result.setFather(rs.getString("father_ID"));
            result.setMother(rs.getString("mother_ID"));
            result.setSpouse(rs.getString("spouse_ID"));
            result.setPersonID(rs.getString("person_ID"));
            persons.add(result);
        }
        //close connection
        rs.close();
        st.close();
        conn.close();
        return persons;
    }

    public boolean hasPerson (String personID) throws SQLException {
        //make connection
        String url = "jdbc:mysql://localhost:3306/FamilyMapDatabase?verifyServerCertificate=false&useSSL=true";
        Connection conn = DriverManager.getConnection(url, "root", "password");
        Statement st = conn.createStatement();

        //execute SQL
        ResultSet rs  = st.executeQuery("SELECT * FROM persons WHERE person_ID = '" + personID + "'");

        //close connection
        if(rs.next()) {
            st.close();
            conn.close();
            return true;
        } else {
            st.close();
            conn.close();
            return false;
        }
    }
}
