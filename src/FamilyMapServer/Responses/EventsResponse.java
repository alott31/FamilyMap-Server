package FamilyMapServer.Responses;

import FamilyMapServer.Event;
import java.util.ArrayList;

public class EventsResponse {

    private ArrayList<Event> events;
    private String message;

    public EventsResponse() {
        events = new ArrayList<Event>();
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public void setMessage(String message) { this.message = message; }

}
