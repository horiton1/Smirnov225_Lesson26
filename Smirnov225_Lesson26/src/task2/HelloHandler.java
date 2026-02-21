package task2;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HelloHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response;

        String method = httpExchange.getRequestMethod();

        response = switch (method) {
            case "POST" -> handlePostRequest(httpExchange);
            case "GET" -> handleGetRequest(httpExchange);
            default -> "Некорректный метод!";
        };

        httpExchange.sendResponseHeaders(200, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private static String handleGetRequest(HttpExchange httpExchange) {
        return "Здравствуйте!";
    }

    private static String handlePostRequest(HttpExchange httpExchange) throws IOException {
        String path = httpExchange.getRequestURI().getPath();
        String[] splitStrings = path.split("/");
        String profession = splitStrings[2];
        String name = splitStrings[3];

        InputStream inputStream = httpExchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        String response = body + ", " + profession + " " + name + "!";

        Headers requestHeaders = httpExchange.getRequestHeaders();
        List<String> wishGoodDay = requestHeaders.get("X-Wish-Good-Day");
        if ((wishGoodDay != null) && (wishGoodDay.contains("true"))) {
            response = response + " Хорошего дня!";
        }

        return response;
    }
}