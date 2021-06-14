package data.posts;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FullPostBody {
    int userId;
    int id;
    String title;
    String body;

    public void setBody(String body) {
        this.body = body;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public String getTitle() {
        return title;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public FullPostBody(){

    }

    public FullPostBody(int id, int userId, String title, String body){
        setId(id);
        setUserId(userId);
        setTitle(title);
        setBody(body);
    }
}