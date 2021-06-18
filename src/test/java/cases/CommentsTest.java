package cases;

import code.BaseTest;
import data.constants.Quantity;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Link;
import io.qameta.allure.Story;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;

import static data.constants.Endpoints.*;
import static data.constants.Quantity.commentsQty;
import static io.restassured.RestAssured.given;

@Epic("JSONPlaceholder API")
@Story("COMMENTS")
@Link("https://jsonplaceholder.typicode.com/")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Check comments endpoints")
public class CommentsTest extends BaseTest {
    private final String commentsJSONSchemaFilename = "schemes/commentsSchema.json";
    private final String commentsToSinglePostJSONSchemaFilename = "schemes/commentsToPostSchema.json";

    @Test
    @Order(1)
    @DisplayName("GET /comments")
    @Description("Должен вернуть JSON из 500 коментов.")
    void GETComments(){
        checkJSONSchema(comments, commentsJSONSchemaFilename);
    }

    @Test
    @Order(2)
    @DisplayName("GET /posts/%d/comments")
    @Description("Ответ GET /posts/%d/comments будет проверен на соответствие схеме")
    void GETCommentsToPost(){
        int id = randomCorrectIndex(commentsQty);
        checkJSONSchemaToSingleObject(commentsToPost, id, commentsToSinglePostJSONSchemaFilename);
    }

    @Test
    @Order(3)
    @DisplayName("GET /comments?postId=%d")
    @Description("А ответ GET /comments?postId=%d будет просто проверен на заголовки и код ответа")
    void GETCommentsToPostV2(){
        int id = randomCorrectIndex(commentsQty);
        checkGETRequest(id, commentsToPostID);
    }

    @Test
    @Order(4)
    @DisplayName("Final checking GET comments")
    @Description("GET /posts/%d/comments and GET /comments?postId=%d should return same response")
    void twoEndpointsOneResponse(){
        checkThatResponsesAreIdentical(commentsToPost, commentsToPostID);
    }
}
