package FamilyMapServer.DAOs;

import FamilyMapServer.Event;
import java.sql.*;
import java.util.ArrayList;

public class EventDAO {



    public void addEvent(Event event) throws SQLException {
        //make connection
        String url = "jdbc:mysql://localhost:3306/FamilyMapDatabase?verifyServerCertificate=false&useSSL=true";
        Connection conn = DriverManager.getConnection(url, "root", "password");
        Statement st = conn.createStatement();

        //execute SQL
        String insert = "insert into events (event_ID, descendant, person_ID, latitude," +
                "longitude, country, city, event_type, year) values ('" +
                event.getEventID() + "', '" +
                event.getDescendant() + "', '" +
                event.getPersonID() + "', '" +
                event.getLatitude() + "', '" +
                event.getLongitude() + "', '" +
                event.getCountry() + "', '" +
                event.getCity() + "', '" +
                event.getEventType() + "', '" +
                event.getYear() + "')";
        st.executeUpdate(insert);

        //close connection
        st.close();
        conn.close();

    }

    public Event getEvent(String eventID) throws SQLException {
        //make connection
        String url = "jdbc:mysql://localhost:3306/FamilyMapDatabase?verifyServerCertificate=false&useSSL=true";
        Connection conn = DriverManager.getConnection(url, "root", "password");
        Statement st = conn.createStatement();

        //execute SQL
        ResultSet rs = st.executeQuery("SELECT * FROM events WHERE event_ID = '" + eventID + "'");

        while(rs.next()) {

            Event result = new Event();
            result.setEventID(eventID);
            result.setDescendant(rs.getString("descendant"));
            result.setPersonID(rs.getString("person_ID"));
            result.setLatitude(rs.getDouble("latitude"));
            result.setLongitude(rs.getDouble("longitude"));
            result.setCountry(rs.getString("country"));
            result.setCity(rs.getString("city"));
            result.setEventType(rs.getString("event_type"));
            result.setYear(rs.getInt("year"));

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
        st.executeUpdate("DELETE FROM events WHERE descendant = '" + username +"'");

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
        st.executeUpdate("truncate events");

        //close connection
        st.close();
        conn.close();
    }

    public ArrayList<Event> getAllEvents(String username) throws SQLException {
        //make connection
        String url = "jdbc:mysql://localhost:3306/FamilyMapDatabase?verifyServerCertificate=false&useSSL=true";
        Connection conn = DriverManager.getConnection(url, "root", "password");
        Statement st = conn.createStatement();

        //execute SQL
        ResultSet rs = st.executeQuery("SELECT * FROM events WHERE descendant = '" + username + "'");
        ArrayList<Event> events = new ArrayList<Event>();

        while (rs.next()) {
            Event result = new Event();
            result.setDescendant(username);
            result.setEventID(rs.getString("event_ID"));
            result.setPersonID(rs.getString("person_ID"));
            result.setLatitude(rs.getDouble("latitude"));
            result.setLongitude(rs.getDouble("longitude"));
            result.setCountry(rs.getString("country"));
            result.setCity(rs.getString("city"));
            result.setEventType(rs.getString("event_type"));
            result.setYear(rs.getInt("year"));
            events.add(result);

        }
        //close connection
        rs.close();
        st.close();
        conn.close();
        return events;
    }

    public boolean hasEvent(String eventID) throws SQLException {
        //make connection
        String url = "jdbc:mysql://localhost:3306/FamilyMapDatabase?verifyServerCertificate=false&useSSL=true";
        Connection conn = DriverManager.getConnection(url, "root", "password");
        Statement st = conn.createStatement();

        //execute SQL
        ResultSet rs  = st.executeQuery("SELECT * FROM events WHERE event_ID = '" + eventID + "'");

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
