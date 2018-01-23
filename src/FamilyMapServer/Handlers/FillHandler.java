package FamilyMapServer.Handlers;

import FamilyMapServer.Responses.FillResponse;
import FamilyMapServer.Services.Fill;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;

public class FillHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        //initialize objects
        boolean success = false;
        int generations;
        Gson gson = new Gson();
        Fill serve = new Fill();

        //parse URI
        URI uri = exchange.getRequestURI();
        String[] segments = uri.getPath().split("/");
        System.out.println(segments.length);
        String username = segments[2];
        if(segments.length == 3) {
            generations = 4;
        } else {
            generations = Integer.parseInt(segments[3]);
        }


        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                //execute service and create response
                FillResponse response = serve.execute(username, generations);
                String respData = gson.toJson(response);

                //send response to HTTP
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                OutputStream respBody = exchange.getResponseBody();
                writeString(respData, respBody);
                respBody.close();
                exchange.getResponseBody().close();
                success = true;
            }
            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (Exception e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
