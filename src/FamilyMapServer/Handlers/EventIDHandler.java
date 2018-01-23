package FamilyMapServer.Handlers;

import FamilyMapServer.DAOs.AuthTokenDAO;
import FamilyMapServer.DAOs.EventDAO;
import FamilyMapServer.Event;
import FamilyMapServer.Responses.EventIDResponse;
import FamilyMapServer.Services.EventIDService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.sql.SQLException;

public class EventIDHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        //initialize objects
        boolean success = false;
        Gson gson = new Gson();
        EventIDService serve = new EventIDService();
        EventDAO dao_event = new EventDAO();
        AuthTokenDAO dao_token = new AuthTokenDAO();

        //split up URI
        URI uri = exchange.getRequestURI();
        String[] segments = uri.getPath().split("/");
        String eventID = segments[2];

        String respData = "";
        EventIDResponse response = new EventIDResponse();

        try {
            if(dao_event.hasEvent(eventID)) { //check that event exists
                Event event = dao_event.getEvent(eventID); //get event data
                if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                    //validate auth token
                    Headers reqHeaders = exchange.getRequestHeaders();
                    if (reqHeaders.containsKey("Authorization")) {
                        String authToken = reqHeaders.getFirst("Authorization");
                        if (dao_token.validate(authToken, event.getDescendant())) {

                            //execute service and create response
                            response = serve.execute(event);
                            respData = gson.toJson(response);

                        } else {
                            response.setMessage("You are not authorized to view this.");
                            respData = gson.toJson(response);
                        }
                    }
                }
                if (!success) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().close();
                }
            } else {
                respData = "{" +
                                "\t\"message\":\"This event ID does not exist.\"" +
                                "}";
            }
            //Send to HTTP
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStream respBody = exchange.getResponseBody();
            writeString(respData, respBody);
            respBody.close();
            exchange.getResponseBody().close();
            success = true;
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
