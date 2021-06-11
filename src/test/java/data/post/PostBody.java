package data.post;

public class PostBody {
    String title;
    String body;
    int userId;

    public void setBody(String body) {
        this.body = body;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
}