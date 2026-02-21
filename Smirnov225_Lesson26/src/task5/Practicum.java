package task5;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class Practicum {
    private static final int PORT = 8080;

    static void main() throws IOException {
        List<Post> posts = new ArrayList<>();
        Post post1 = new Post(1, "Это первый пост, который я здесь написал.");
        post1.addComment(new Comment("Пётр Первый", "Я успел откомментировать первым!"));
        posts.add(post1);

        Post post2 = new Post(22, "Это будет второй пост. Тоже короткий.");
        posts.add(post2);

        Post post3 = new Post(333, "Это пока последний пост.");
        posts.add(post3);

        HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/posts", new PostsHandler(posts));
        httpServer.start();

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
        httpServer.stop(1);
    }
}