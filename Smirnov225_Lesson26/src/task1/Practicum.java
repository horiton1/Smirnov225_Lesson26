package task1;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.DayOfWeek;
import java.util.Random;

public class Practicum {
    private static final int PORT = 8080;

    static void main() throws IOException {
        HttpServer httpServer = HttpServer.create();

        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/hello", new HelloHandler());
        httpServer.createContext("/day", new DayHandler());
        httpServer.start();

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
        httpServer.stop(1);
    }

    static class HelloHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            System.out.println("Началась обработка /day запроса от клиента.");

            String response = "Hey! Glad to see you on our server.";
            httpExchange.sendResponseHeaders(200, 0);

            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    static class DayHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            System.out.println("Началась обработка /day запроса от клиента.");
            
            Random random = new Random();
            int randomDayNumber = random.nextInt(7) + 1;
            String response = DayOfWeek.of(randomDayNumber).name();
            
            httpExchange.sendResponseHeaders(200, 0);
            
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}