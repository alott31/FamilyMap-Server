package FamilyMapServer.Handlers;

import FamilyMapServer.Requests.LoadRequest;
import FamilyMapServer.Responses.LoadResponse;
import FamilyMapServer.Services.Load;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.sql.SQLException;

public class LoadHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        //initialize objects
        boolean success = false;
        Gson gson = new Gson();
        Load serve = new Load();

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                //get and print request
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);
                System.out.println(reqData);

                //execute service and create response
                LoadRequest request = gson.fromJson(reqData, LoadRequest.class);
                LoadResponse response = serve.execute(request);
                String respData = gson.toJson(response);

                //send response to HTTP
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
