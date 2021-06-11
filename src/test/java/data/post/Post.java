package data.post;

import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("headers.Content-type")
    private String contentType;
    private PostBody body;

    public void setBody(PostBody body) {
        this.body = body;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public PostBody getBody() {
        return body;
    }

    public String getContentType() {
        return contentType;
    }
}
