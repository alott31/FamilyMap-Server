package FamilyMapServer.Handlers;

import FamilyMapServer.Requests.LoginRequest;
import FamilyMapServer.Responses.LoginResponse;
import FamilyMapServer.Services.Login;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class LoginHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;
        Gson gson = new Gson();
        Login serve = new Login();

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                //get request
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);
                System.out.println(reqData);
                LoginRequest request = gson.fromJson(reqData, LoginRequest.class);

                //execute service and create response
                LoginResponse response = serve.execute(request);
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
        catch (Exception e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
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
