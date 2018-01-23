package FamilyMapServer.Responses;

public class PersonIDResponse {

    private String personID;
    private String descendant;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }
}
