package week6;


import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.*;


public class Server {
    HttpServer server;
    List<User> users;
    static String key = "";


    public Server() {
        try {
            server = HttpServer.create(new InetSocketAddress(8080), 0);
            users = new ArrayList<>();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addUsers(List<User> newUsers) {
        users.addAll(newUsers);
    }


    public String jsonFromResponse(HttpExchange httpExchange) {
        Scanner scanner = new Scanner(httpExchange.getRequestBody());
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNext()) {
            builder.append(scanner.nextLine());

        }
        return builder.toString();
    }

    public void login() {


        server.createContext("/login", new HttpHandler() {

            @Override
            public void handle(HttpExchange httpExchange) throws IOException {

                User parsedUser = new Gson().fromJson(jsonFromResponse(httpExchange), User.class);
                OutputStream stream = httpExchange.getResponseBody();
                String accessKey = "{ \"accessKey\" : \"" + users.stream()
                        .filter(user -> user.getUser().equals(parsedUser.getUser())
                                && user.getPass().equals(parsedUser.getPass()))
                        .findFirst()
                        .map(user -> Base64.getEncoder()
                                .encodeToString((user.getUser() + user.getPass()).getBytes()))
                        .get() + "\" }";
                key = accessKey;
                byte[] bytes = accessKey.getBytes();
                httpExchange.sendResponseHeaders(200, bytes.length);
                stream.write(bytes);
                stream.flush();
                stream.close();

            }
        });

        server.setExecutor(null);
        server.start();
    }



//    return result;
//        "name" : "Kolia Kers",
//            "posts" : [
//        {
//            "id" : 23425,
//                "title" : "Title",
//                "body": "Message Body"
//        }, {}, {}, {}
//            ]
//
//    }

    public void getUser(){
        server.createContext("/username/?" , new HttpHandler() {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException {
                if(httpExchange.getRequestHeaders().containsValue(Server.key)) {

                    String username = httpExchange.getRequestURI()
                            .getQuery().split("/")[2];
                    User userString = users.stream()
                            .filter(user -> user.getUser().equals(username))
                            .findFirst()
                            .get();

                    StringBuilder builder = new StringBuilder();
                    builder.append("{ \"name\":\"" + userString.getName()
                    + "\", \"posts\":" + Arrays.toString(userString.getPosts().toArray()));

                    OutputStream stream = httpExchange.getResponseBody();

                    httpExchange.sendResponseHeaders(200, builder.toString().length());
                    stream.write(builder.toString().getBytes());
                    stream.flush();
                    stream.close();
                }
            }
        });

    }



    public void find(String key) {

    }

//     "/user/find?key=Kolia"
//    in headers we must have "accessKey" header
//    resqp:
//            [{
//        "name": "Kolia Kers",
//                "url" : "/user/kol32"
//    },
//    {},
//    {}]

}
