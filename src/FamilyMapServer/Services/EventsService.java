package FamilyMapServer.Services;

import FamilyMapServer.DAOs.EventDAO;
import FamilyMapServer.Responses.EventsResponse;

import java.sql.SQLException;

public class EventsService {

    public EventsResponse execute(String username) throws SQLException {

        EventDAO dao = new EventDAO();
        EventsResponse resp = new EventsResponse();
        resp.setEvents(dao.getAllEvents(username));
        return resp;

    }
}
