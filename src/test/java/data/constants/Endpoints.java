package data.constants;

public interface Endpoints {
    String BASE_URI = "http://jsonplaceholder.typicode.com";
    String posts = "/posts";
    String post = posts + "/{id}";
    String comments = "/comments";
    String commentsToPost = post + "/comments";
    String commentsToPostID = comments + "?postId={id}";
}
