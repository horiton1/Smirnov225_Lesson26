package task3;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class PostsHandler implements HttpHandler {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

        switch (endpoint) {
            case GET_POSTS: {
                writeResponse(exchange, "Получен запрос на получение постов", 200);
                break;
            }
            case GET_COMMENTS: {
                writeResponse(exchange, "Получен запрос на получение комментариев", 200);
                break;
            }
            case POST_COMMENT: {
                writeResponse(exchange, "Получен запрос на добавление комментария", 200);
                break;
            }
            default:
                writeResponse(exchange, "Такого эндпоинта не существует", 404);
        }
    }

    private Endpoint getEndpoint(String requestPath, String requestMethod) {
        String[] pathParts = requestPath.split("/");

        if (pathParts.length == 2 && pathParts[1].equals("posts")) {
            return Endpoint.GET_POSTS;
        }

        if (pathParts.length == 4 && pathParts[1].equals("posts") && pathParts[3].equals("comments")) {
            if (requestMethod.equals("GET")) {
                return Endpoint.GET_COMMENTS;
            }
            if (requestMethod.equals("POST")) {
                return Endpoint.POST_COMMENT;
            }
        }

        return Endpoint.UNKNOWN;
    }

    private void writeResponse(HttpExchange exchange,
                               String responseString,
                               int responseCode) throws IOException {
        if (responseString.isEmpty()) {
            exchange.sendResponseHeaders(responseCode, -1);
        } else {
            exchange.sendResponseHeaders(responseCode, 0);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseString.getBytes(DEFAULT_CHARSET));
            }
        }
    }

    enum Endpoint {GET_POSTS, GET_COMMENTS, POST_COMMENT, UNKNOWN}
}