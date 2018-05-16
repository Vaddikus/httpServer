package week6;

import java.util.Arrays;
import java.util.List;

public class StartServer {

    public static void main(String[] args) {

        Server server = new Server();
        List<User> users = Arrays.asList(new User("Elvis Presley", "presly", "12345"),
                new User("Vad Vadym", "vadoskin", "password123"),
                new User("Mykola Mykolenko", "kolyan", "12345"),
                new User("Max Maximov", "maxxis", "55555")
                );

        users.get(0).setPosts(Arrays.asList(new Post("WTF", "test post")
                , new Post("#2", "test post2")));
        server.addUsers(users);
        server.login();
        server.getUser();
    }
}
