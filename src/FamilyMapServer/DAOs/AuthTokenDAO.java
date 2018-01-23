package FamilyMapServer.DAOs;

import FamilyMapServer.AuthToken;
import FamilyMapServer.Server;

import java.sql.*;

public class AuthTokenDAO {

    private long timeLimit;

    public AuthTokenDAO() {
        Server server = new Server();
        timeLimit = server.getTime();
    }

    public void addAuthToken(AuthToken token) throws SQLException {
        //establish connection
        String url = "jdbc:mysql://localhost:3306/FamilyMapDatabase?verifyServerCertificate=false&useSSL=true";
        Connection conn = DriverManager.getConnection(url, "root", "password");
        Statement st = conn.createStatement();

        //SQL statement
        String insert = "insert into auth_tokens " +
                "(auth_token, username, time) values ('" +
                token.getToken() + "', '" +
                token.getUsername() + "', '" +
                System.currentTimeMillis() + "')";
        st.executeUpdate(insert);

        //close connection
        st.close();
        conn.close();
    }

    public String getUsername(String token) throws SQLException {
        //establish connection
        String username = "";
        String url = "jdbc:mysql://localhost:3306/FamilyMapDatabase?verifyServerCertificate=false&useSSL=true";
        Connection conn = DriverManager.getConnection(url, "root", "password");
        Statement st = conn.createStatement();

        //SQL statement
        String select = "SELECT * from auth_tokens WHERE auth_token = '" + token + "'";
        ResultSet rs = st.executeQuery(select);

        while(rs.next()) {
            username = rs.getString("username");
        }

        //close connection
        st.close();
        conn.close();

        return username;
    }

    public boolean validate(String token, String user) throws SQLException {
        //establish connection
        String url = "jdbc:mysql://localhost:3306/FamilyMapDatabase?verifyServerCertificate=false&useSSL=true";
        Connection conn = DriverManager.getConnection(url, "root", "password");
        Statement st = conn.createStatement();

        String select = "SELECT * from auth_tokens WHERE auth_token = '" + token + "'";
        ResultSet rs = st.executeQuery(select);
        while(rs.next()) {
            String tokenDB = rs.getString("auth_token");
            String usernameDB = rs.getString("username");
            String timeDB = rs.getString("time");


            if (tokenDB.equals(token)) {
                if(usernameDB.equals(user)) {
                    if(System.currentTimeMillis() - Long.parseLong(timeDB) < timeLimit) {
                        rs.close();
                        st.close();
                        conn.close();
                        return true;
                    }
                //execute SQL
                    st.executeUpdate("DELETE FROM auth_tokens WHERE auth_token = '" + token + "'");
                }
            }
            //close connection
            rs.close();
            st.close();
            conn.close();
            return false;
        }
        rs.close();
        st.close();
        conn.close();
        return false;
    }

    public void clear() throws SQLException {
        //establish connection
        String url = "jdbc:mysql://localhost:3306/FamilyMapDatabase?verifyServerCertificate=false&useSSL=true";
        Connection conn = DriverManager.getConnection(url, "root", "password");
        Statement st = conn.createStatement();

        //execute SQL
        st.executeUpdate("TRUNCATE auth_tokens");

        //close connection
        st.close();
        conn.close();
    }

}
