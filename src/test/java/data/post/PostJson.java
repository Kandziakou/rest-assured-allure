package data.post;

import com.google.gson.annotations.SerializedName;

public class PostJson {
    @SerializedName("headers.Content-type")
    public String contentType = "application/json; charset=UTF-8";
    public Body body;

    public PostJson(){
        body = new Body("foo", "bar", "1");
    }
}

class Body{
    String title;
    String body;
    String userId;

    Body(String title, String body, String userId){
        this.title = title;
        this.body = body;
        this.userId = userId;
    }
}
