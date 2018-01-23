package FamilyMapServer.DAOs;

import FamilyMapServer.User;

import java.sql.*;

public class UserDAO {

    public void addUser(User user) throws SQLException {
        //make connection
        String url = "jdbc:mysql://localhost:3306/FamilyMapDatabase?verifyServerCertificate=false&useSSL=true";
        Connection conn = DriverManager.getConnection(url, "root", "password");
        Statement st = conn.createStatement();

        //execute SQL
        String insert = "insert into users " +
                "(person_ID, username, password, email_address, " +
                "first_name, last_name, gender) values ('" +
                user.getPersonID() + "', '" +
                user.getUserName() + "', '" +
                user.getPassword() + "', '" +
                user.getEmail() + "', '" +
                user.getFirstName() + "', '" +
                user.getLastName() + "', '" +
                user.getGender() + "')";
        st.executeUpdate(insert);

        //close connection
        st.close();
        conn.close();
    }

    public User getUser(String username) throws SQLException {
        //make connection
        String url = "jdbc:mysql://localhost:3306/FamilyMapDatabase?verifyServerCertificate=false&useSSL=true";
        Connection conn = DriverManager.getConnection(url, "root", "password");
        Statement st = conn.createStatement();

        //execute SQL
        ResultSet rs = st.executeQuery("SELECT * FROM users WHERE username = '" + username + "'");
        while(rs.next()) {
            User result = new User();
            result.setUserName(username);
            result.setPassword(rs.getString("password"));
            result.setEmail(rs.getString("email_address"));
            result.setFirstName(rs.getString("first_name"));
            result.setLastName(rs.getString("last_name"));
            result.setGender(rs.getString("gender"));
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

    public void clear() throws SQLException {
        //make connection
        String url = "jdbc:mysql://localhost:3306/FamilyMapDatabase?verifyServerCertificate=false&useSSL=true";
        Connection conn = DriverManager.getConnection(url, "root", "password");
        Statement st = conn.createStatement();

        //execute SQL
        st.executeUpdate("TRUNCATE users");

        //close connection
        st.close();
        conn.close();
    }

    public boolean hasUser(String username) throws SQLException {
        //make connection
        String url = "jdbc:mysql://localhost:3306/FamilyMapDatabase?verifyServerCertificate=false&useSSL=true";
        Connection conn = DriverManager.getConnection(url, "root", "password");
        Statement st = conn.createStatement();

        //execute SQL
        ResultSet rs  = st.executeQuery("SELECT * FROM users WHERE username = '" + username + "'");

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
