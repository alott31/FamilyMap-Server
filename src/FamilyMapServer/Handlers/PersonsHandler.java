package FamilyMapServer.Handlers;

import FamilyMapServer.DAOs.AuthTokenDAO;
import FamilyMapServer.Responses.PersonsResponse;
import FamilyMapServer.Services.PersonsService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.sql.SQLException;

public class PersonsHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        //initialize objects
        boolean success = false;
        Gson gson = new Gson();
        PersonsService serve = new PersonsService();
        AuthTokenDAO dao_token = new AuthTokenDAO();
        String respData = "";
        PersonsResponse response = new PersonsResponse();

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                //validate auth token
                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");
                    String username = dao_token.getUsername(authToken);
                    if (dao_token.validate(authToken, username)) {

                        //execute service and create response
                        response = serve.execute(username);
                        respData = gson.toJson(response);

                    } else {
                        response.setMessage("You are not authorized to view this.");
                        respData = gson.toJson(response);
                    }

                    //send to HTTP
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(respData, respBody);
                    respBody.close();
                    exchange.getResponseBody().close();
                    success = true;
                }
            }

        //error handling
            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
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
