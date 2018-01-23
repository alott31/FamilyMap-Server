package FamilyMapServer.Handlers;

import FamilyMapServer.Responses.ClearResponse;
import FamilyMapServer.Services.Clear;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class ClearHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;
        Gson gson = new Gson();
        Clear serve = new Clear();

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                ClearResponse response = serve.execute();
                String respData = gson.toJson(response);

                //send to HTTP
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                OutputStream respBody = exchange.getResponseBody();
                writeString(respData, respBody);
                respBody.close();
                exchange.getResponseBody().close();
                success = true;
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
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
