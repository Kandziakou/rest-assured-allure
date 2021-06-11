package cases;

import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import data.post.PostBody;
import data.post.Post;
import io.qameta.allure.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Checking endpoints /posts and /posts/%d")
public class PostsTest extends BaseTest{
    private final Post POSTorPUTPost = new Post();
    private final Post PATCHPost = new Post();
    private int id = 0;

    PostsTest(){
        PostBody body = new PostBody();
        body.setUserId(1);
        body.setTitle("foo");
        body.setBody("bar");
        POSTorPUTPost.setBody(body);

        PostBody partialBody = new PostBody();
        partialBody.setUserId(9999);
        partialBody.setTitle("FOO");
        PATCHPost.setBody(partialBody);
    }

    @Test
    @Order(1)
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("GET /posts")
    @Description("GET /posts должно вернуть JSON c ответом 200")
    void GETPosts(){
        GETAllPosts();
    }

    @Test
    @Order(1)
    @DisplayName("GET /posts v.2")
    @Description("JSON schema validation for GET /posts")
    void JSONSchemaValidateForGETPosts(){
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder().setValidationConfiguration(ValidationConfiguration.newBuilder().freeze()).freeze();
        given().baseUri(URI)
                .when().get(posts)
                .then().assertThat().body(matchesJsonSchemaInClasspath("postsSchema.json").using(jsonSchemaFactory));
    }

    @Test
    @Order(2)
    @DisplayName("GET /posts/%d")
    @Description("GET /posts/%d должно вернуть JSON с ответом 200 для существующего поста и 404- для несуществующего")
    void GETPost(){
        GETCorrectPost();
        GETIncorrectPost();
    }

    @Test
    @Order(3)
    @DisplayName("POST /posts")
    @Description("POST /posts должно отправить сообщение и вернуть ответ с id=101 и кодом ответа 201")
    void POSTPosts(){
        given().baseUri(URI).contentType(ContentType.JSON).body(POSTorPUTPost.getBody())
                .when().post(posts)
                .then().assertThat().statusCode(201).body("id", equalTo(101));
    }

    @Test
    @Order(4)
    @Severity(SeverityLevel.MINOR)
    @DisplayName("PUT /posts/%d")
    @Description("PUT /posts/%d должно изменить тело сообщения id=%d и вернуть новое тело сообщения с кодом 200")
    void PUTPost(){
        id = randomCorrectIndex(postsQty);
        checkPost(id);
        PUTNewBodyToPost(id, POSTorPUTPost);
    }

    @Test
    @Order(5)
    @DisplayName("PATCH /posts/%d")
    @Description("PATCH /posts/%d должен изменить указанные поля и вернуть тело сообщения с кодом 200")
    void PATCHPost(){
        id = randomCorrectIndex(postsQty);
        checkPost(id);
        String randPost = String.format(post, id);
        String originalPostBody = given().get(URI+randPost).then().extract().body().as(PostBody.class).getBody();
        RequestSpecification request = new RequestSpecBuilder()
                .setBaseUri(URI)
                .setContentType(ContentType.JSON)
                .setBody(PATCHPost.getBody()).build();
        ResponseSpecification response = new ResponseSpecBuilder()
                .expectStatusCode(200).expectContentType(ContentType.JSON)
                .expectBody("title", equalTo(PATCHPost.getBody().getTitle()))
                .expectBody("body", equalTo(originalPostBody))
                .expectBody("userId", equalTo(PATCHPost.getBody().getUserId())).build();
        attachResponse(given().when().spec(request).patch(randPost).then().spec(response));
    }

    @Test
    @Order(6)
    @DisplayName("DELETE /posts/%d")
    @Description("DELETE /posts/%d должен вернуть пустое тело с кодом 200")
    void DELETEPost(){
        id = randomCorrectIndex(postsQty);
        checkPost(id);
        String randPost = String.format(post, id);
        attachResponse(given().baseUri(URI).when().delete(randPost).then().assertThat().statusCode(200).body(equalTo("{}")));
    }

    //todo разобраться с null в PATCH response
    //todo разобраться с возвращаемым значением attachExpectedResponseToCorrectRequest
}
