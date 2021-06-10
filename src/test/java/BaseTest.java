import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

public class BaseTest {
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

    private int randomCorrectIndex(int qty){
        return (int) (Math.random() * qty);
    }

    protected void GETCorrectPost(){
        correctGET();
        String path = String.format(post, randomCorrectIndex(postsQty));
        given().spec(request).when().get(path).then().spec(response);
    }

    protected void GETIncorrectPost(){
        incorrectGET();
        String path = String.format(post, randomCorrectIndex(postsQty)+postsQty);
        given().spec(request).when().get(path).then().spec(response);
    }

    protected void GETAllPosts(){
        correctGET();
        given().spec(request).when().get(posts).then().spec(response);
    }
}
