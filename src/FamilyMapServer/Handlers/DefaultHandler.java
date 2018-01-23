package FamilyMapServer.Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DefaultHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {

            String path;
            String uri = exchange.getRequestURI().toString();

            //determine the request path
            if(uri.equals("/")) path = "/Users/Spencer/Documents/School/C S 240/FamilyMapServer [final]/web/index.html";
            else path = "/Users/Spencer/Documents/School/C S 240/FamilyMapServer [final]/web" + exchange.getRequestURI();

            //obtain file from "web" directory within this project
            Path source = Paths.get(path);

            //send to HTTP
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStream respBody = exchange.getResponseBody();
            Files.copy(source, respBody);
            respBody.close();

        } catch (IOException e) {

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

}
