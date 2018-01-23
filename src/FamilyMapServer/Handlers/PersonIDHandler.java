package FamilyMapServer.Handlers;

import FamilyMapServer.DAOs.AuthTokenDAO;
import FamilyMapServer.DAOs.PersonDAO;
import FamilyMapServer.Person;
import FamilyMapServer.Responses.PersonIDResponse;
import FamilyMapServer.Services.PersonIDService;
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

public class PersonIDHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        //initialize objects
        boolean success = false;
        Gson gson = new Gson();
        PersonIDService serve = new PersonIDService();
        PersonDAO dao_person = new PersonDAO();
        AuthTokenDAO dao_token = new AuthTokenDAO();
        PersonIDResponse response = new PersonIDResponse();
        String respData = "";

        //parse URI
        URI uri = exchange.getRequestURI();
        String[] segments = uri.getPath().split("/");
        String personID = segments[2];


        try {
            //ensure person exists then get person data
            if(dao_person.hasPerson(personID)) {
                Person person = dao_person.getPerson(personID);
                if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                    //validate auth token
                    Headers reqHeaders = exchange.getRequestHeaders();
                    if (reqHeaders.containsKey("Authorization")) {
                        String authToken = reqHeaders.getFirst("Authorization");
                        if (dao_token.validate(authToken, person.getDescendant())) {

                            //execute service and create response
                            response = serve.execute(person);
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
                response.setMessage("This Person ID does not exist.");
                respData = gson.toJson(response);
            }

            //send to HTTP
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStream respBody = exchange.getResponseBody();
            writeString(respData, respBody);
            respBody.close();
            exchange.getResponseBody().close();
            success = true;


        //error handling
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
