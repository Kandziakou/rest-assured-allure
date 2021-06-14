package code;

import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import data.posts.FullPostBody;
import data.posts.PartialPostBody;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Assertions;

import static data.constants.Endpoints.*;
import static data.constants.Quantity.commentsQty;
import static data.constants.Quantity.postsQty;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class BaseTest extends Attachments {
    private RequestSpecification request;
    private ResponseSpecification response;

    protected void correctGET(){
        request = new RequestSpecBuilder()
               .setBaseUri(BASE_URI).setPort(80).build();
        response = new ResponseSpecBuilder()
               .expectContentType(ContentType.JSON).expectStatusCode(200).build();
    }

    private void incorrectGET(){
        request = new RequestSpecBuilder()
                .setBaseUri(BASE_URI).setPort(80).build();
        response = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON).expectStatusCode(404).build();
    }

    protected int randomCorrectIndex(int qty){
        return (int) (Math.random() * qty);
    }

    @Step("Check that original post are different from sent with PUT request")
    protected void checkPost(int id, FullPostBody newPost){
        given().baseUri(BASE_URI)
                .pathParam("id", id)
                .when().get(post)
                .then().assertThat()
                .body("title", not(newPost.getTitle()))
                .body("body", not(newPost.getBody()))
                .body("id", equalTo(id));
    }

    @Step("Check that post /posts/{id} are exist")
    protected void checkPost(int id){
        given().baseUri(BASE_URI)
                .pathParam("id", id)
                .when().get(post)
                .then().assertThat()
                .body(not("{/n/n}"));
    }

    @Step("Check that PUT request change post body")
    protected void sendPUTRequest(int id, FullPostBody newPost){
        given().baseUri(BASE_URI).pathParam("id", id)
                .contentType(ContentType.JSON).body(newPost)
                .when().put(post)
                .then().assertThat()
                .statusCode(200)
                .body("title", equalTo("foo"))
                .body("id", equalTo(id));
    }

    @Step("Check that GET request return 200 code and type of response body are JSON")
    protected void checkGETRequest(int id, String path){
        given().baseUri(BASE_URI).pathParam("id", id)
                .when().get(path)
                .then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    protected void checkThatResponsesAreIdentical(String path1, String path2){
        int id = randomCorrectIndex(commentsQty);
        Response response1 = given().baseUri(BASE_URI).pathParam("id", id).get(path1);
        Response response2 = given().baseUri(BASE_URI).pathParam("id", id).get(path2);
        attachResponse(response1, "GET /posts/id/comments");
        attachResponse(response2, "GET /comments?postId=id");
        Assertions.assertEquals(response1.getBody().asPrettyString(), response2.getBody().asPrettyString());
    }

    @Step("Sending correct GET request")
    protected void GETCorrectPost(){
        correctGET();
        int id = randomCorrectIndex(postsQty);
        responseForGETPost(id);
    }

    @Step("Sending incorrect GET request")
    protected void GETIncorrectPost(){
        incorrectGET();
        int id = randomCorrectIndex(postsQty)+postsQty;
        responseForGETPost(id);
    }

    @Step("Sending GET /posts request")
    protected void GETAllPosts(){
        correctGET();

        attachExpectedResponseToGETPosts();
        attachResponse(given().spec(request).when().get(posts).then().spec(response));
    }

    @Step("Sending POST request")
    protected void sendPOSTRequest(String endpoint, Object body){
        ValidatableResponse response = given().baseUri(BASE_URI).contentType(ContentType.JSON).body(body)
                .when().post(endpoint)
                .then().assertThat().statusCode(201).body("id", equalTo(101));
        attachResponse(response);
    }

    @Step("Sending PATCH request")
    protected  void sendPATCHRequest(int id, PartialPostBody partialPost){
        Response originalPost = given().baseUri(BASE_URI).pathParam("id", id).get(post);
        RequestSpecification request = new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setContentType(ContentType.JSON)
                .setBody(partialPost).build();
        ResponseSpecification response = new ResponseSpecBuilder()
                .expectStatusCode(200).expectContentType(ContentType.JSON)
                .expectBody("title", equalTo(partialPost.getTitle()))
                .expectBody("body", equalTo(originalPost.as(FullPostBody.class).getBody()))
                .expectBody("userId", equalTo(partialPost.getUserId())).build();
        ValidatableResponse patchedPost = given().pathParam("id", id)
                .when().spec(request).patch(post)
                .then().spec(response);
        Allure.addAttachment("Original post", "txt/json", originalPost.asPrettyString(), "json");
        Allure.addAttachment("Response after PATCH", "txt/json", patchedPost.extract().asPrettyString(), "json");
    }

    @Step("Sending DELETE request")
    protected void sendDELETERequest(int id){
        attachResponse(given().baseUri(BASE_URI).pathParam("id", id)
                .when().delete(post)
                .then().assertThat().statusCode(200).body(equalTo("{}")));
    }

    private void responseForGETPost(int id){
        ValidatableResponse resp = given().pathParam("id", id).spec(request).when().get(post).then().spec(response);
        attachExpectedResponse(id);
        attachResponse(resp);
    }

    protected void checkJSONSchema(String endpoint, String schema){
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder()
                .setValidationConfiguration(ValidationConfiguration.newBuilder().freeze()).freeze();
        given().baseUri(BASE_URI)
                .when().get(endpoint)
                .then().assertThat().body(matchesJsonSchemaInClasspath(schema).using(jsonSchemaFactory));
    }

    protected void checkJSONSchemaToSingleObject(String endpoint, int id, String schema){
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder()
                .setValidationConfiguration(ValidationConfiguration.newBuilder().freeze()).freeze();
        given().baseUri(BASE_URI).pathParam("id", id)
                .when().get(endpoint)
                .then().assertThat().body(matchesJsonSchemaInClasspath(schema).using(jsonSchemaFactory));

    }
}
