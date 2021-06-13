package cases;

import data.post.Post;
import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class BaseTest extends Response{
    private RequestSpecification request;
    private ResponseSpecification response;

    String URI = "http://jsonplaceholder.typicode.com/";
    String posts = "/posts";
    String post = posts + "/%d";
    int postsQty = 100;

    protected void correctGET(){
        request = new RequestSpecBuilder()
               .setBaseUri(URI).setPort(80).build();
        response = new ResponseSpecBuilder()
               .expectContentType(ContentType.JSON).expectStatusCode(200).build();
    }

    private void incorrectGET(){
        request = new RequestSpecBuilder()
                .setBaseUri(URI).setPort(80).build();
        response = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON).expectStatusCode(404).build();
    }

    protected int randomCorrectIndex(int qty){
        return (int) (Math.random() * qty);
    }

    protected void checkPost(int id){
        String randPost = String.format(post, id);
        given().baseUri(URI)
                .when().get(randPost)
                .then().assertThat()
                .body("title", not("foo"))
                .body("body", not("bar"))
                .body("id", equalTo(id));
    }

    protected void PUTNewBodyToPost(int id, Post newPost){
        String randPost = String.format(post, id);
        given().baseUri(URI).contentType(ContentType.JSON).body(newPost.getBody())
                .when().put(randPost)
                .then().assertThat()
                .statusCode(200)
                .body("title", equalTo("foo"))
                .body("id", equalTo(id));
    }

    @Step("Sending correct GET request")
    protected void GETCorrectPost(){
        correctGET();
        int id = randomCorrectIndex(postsQty);
        String path = String.format(post, id);
        attachExpectedResponseToCorrectRequest(id);
        attachResponse(given().spec(request).when().get(path).then().spec(response));
    }

    @Step("Sending incorrect GET request")
    protected void GETIncorrectPost(){
        incorrectGET();
        int id = randomCorrectIndex(postsQty)+postsQty;
        String path = String.format(post, id);
        attachExpectedResponseToIncorrectRequest();
        attachResponse(given().spec(request).when().get(path).then().spec(response));
    }

    @Step("Sending GET /posts request")
    protected void GETAllPosts(){
        correctGET();
        attachExpectedResponseToCorrectRequest();
        attachResponse(given().spec(request).when().get(posts).then().spec(response));
    }
}
