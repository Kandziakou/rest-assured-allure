package data.posts;

public class PartialPostBody {
    int id;
    int userId;
    String title;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setId(int id) {
        this.id = id;
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

    public PartialPostBody(){

    }

    public PartialPostBody(int id, int userId, String title){
        setId(id);
        setUserId(userId);
        setTitle(title);
    }
}
