package FamilyMapServer.Services;

import FamilyMapServer.Event;
import FamilyMapServer.Responses.EventIDResponse;
import com.google.gson.Gson;

public class EventIDService {

    public EventIDResponse execute(Event event) {

        Gson gson = new Gson();

        EventIDResponse resp = gson.fromJson(gson.toJson(event), EventIDResponse.class);

        if (resp == null) {
            resp.setMessage("This Event ID does not exist.");
        }

        return resp;
    }

}
