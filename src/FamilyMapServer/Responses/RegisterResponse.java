package FamilyMapServer.Responses;

public class RegisterResponse {

    private String token;
    private String user;
    private String personID;

    private String message;

    public void setToken(String token) { this.token = token; }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }


    public void setMessage(String message) { this.message = message;}
}
