package FamilyMapServer.Services;

import FamilyMapServer.Person;
import FamilyMapServer.Responses.PersonIDResponse;
import com.google.gson.Gson;

public class PersonIDService {

    public PersonIDResponse execute(Person person) {

        Gson gson = new Gson();
        PersonIDResponse resp = gson.fromJson(gson.toJson(person), PersonIDResponse.class);
        return resp;

    }
}
