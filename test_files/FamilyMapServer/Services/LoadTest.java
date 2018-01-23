package FamilyMapServer.Services;

import FamilyMapServer.DAOs.UserDAO;
import FamilyMapServer.Requests.LoadRequest;
import com.google.gson.Gson;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoadTest {
    @Test
    public void execute() throws Exception {
        Gson gson = new Gson();
        LoadRequest req = gson.fromJson(data(), LoadRequest.class);
        Load test = new Load();
        test.execute(req);

        UserDAO dao = new UserDAO();
        assertTrue(dao.hasUser("sheila"));
    }

    private String data() {
        String data = "{\n" +
                "  \"users\": [\n" +
                "    {\n" +
                "      \"userName\": \"sheila\",\n" +
                "      \"password\": \"parker\",\n" +
                "      \"email\": \"sheila@parker.com\",\n" +
                "      \"firstName\": \"Sheila\",\n" +
                "      \"lastName\": \"Parker\",\n" +
                "      \"gender\": \"f\",\n" +
                "      \"personID\": \"Sheila_Parker\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"persons\": [\n" +
                "    {\n" +
                "      \"firstName\": \"Sheila\",\n" +
                "      \"lastName\": \"Parker\",\n" +
                "      \"gender\": \"f\",\n" +
                "      \"personID\": \"Sheila_Parker\",\n" +
                "      \"father\": \"Patrick_Spencer\",\n" +
                "      \"mother\": \"Im_really_good_at_names\",\n" +
                "      \"descendant\": \"sheila\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"firstName\": \"Patrick\",\n" +
                "      \"lastName\": \"Spencer\",\n" +
                "      \"gender\": \"m\",\n" +
                "      \"personID\":\"Patrick_Spencer\",\n" +
                "      \"spouse\": \"Im_really_good_at_names\",\n" +
                "      \"descendant\": \"sheila\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"firstName\": \"CS240\",\n" +
                "      \"lastName\": \"JavaRocks\",\n" +
                "      \"gender\": \"f\",\n" +
                "      \"personID\": \"Im_really_good_at_names\",\n" +
                "      \"spouse\": \"Patrick_Spencer\",\n" +
                "      \"descendant\": \"sheila\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"events\": [\n" +
                "    {\n" +
                "      \"eventType\": \"started family map\",\n" +
                "      \"personID\": \"Sheila_Parker\",\n" +
                "      \"city\": \"Salt Lake City\",\n" +
                "      \"country\": \"United States\",\n" +
                "      \"latitude\": 40.7500,\n" +
                "      \"longitude\": -110.1167,\n" +
                "      \"year\": 2016,\n" +
                "      \"eventID\": \"Sheila_Family_Map\",\n" +
                "      \"descendant\":\"sheila\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"eventType\": \"fixed this thing\",\n" +
                "      \"personID\": \"Patrick_Spencer\",\n" +
                "      \"city\": \"Provo\",\n" +
                "      \"country\": \"United States\",\n" +
                "      \"latitude\": 40.2338,\n" +
                "      \"longitude\": -111.6585,\n" +
                "      \"year\": 2017,\n" +
                "      \"eventID\": \"I_hate_formatting\",\n" +
                "      \"descendant\": \"sheila\"\n" +
                "    }\n" +
                "  ]\n" +
                "}\n";
        return data;
    }
}