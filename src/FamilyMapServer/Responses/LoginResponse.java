package FamilyMapServer.Responses;

public class LoginResponse {

    private String token;
    private String user;
    private String personID;

    private String message;

    public void setToken(String token) { this.token = token; }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPersonID(String personID) { this.personID = personID; }


    //getters and setters for message
    public void setMessage(String message) { this.message = message; }
    public String getMessage() { return message; }

}
