package week6;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;
    private String user;
    private String pass;

    private List<Post> posts;

    public User(){

     }

    public User(String name, String user, String pass) {
        this.name = name;
        this.user = user;
        this.pass = pass;
        posts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
