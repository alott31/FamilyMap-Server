package FamilyMapServer.Responses;

public class EventIDResponse {

    private String eventID;
    private String descendant;
    private String personID;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    private String message;

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
