package cases;

import code.BaseTest;
import data.posts.FullPostBody;
import data.posts.PartialPostBody;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import static data.constants.Endpoints.*;
import static data.constants.Quantity.postsQty;

@Epic("JSONPlaceholder API")
@Story("POSTS")
@Link("https://jsonplaceholder.typicode.com/")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Checking endpoints /posts and /posts/id")
public class PostsTest extends BaseTest {
    private int id = 1;
    private FullPostBody fullPost = new FullPostBody(id, 1, "foo", "bar");
    private PartialPostBody partialPost;
    private String postsJSONSchemaFilename = "schemes/postsSchema.json";

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
        checkJSONSchema(posts, postsJSONSchemaFilename);
    }

    @Test
    @Order(2)
    @DisplayName("GET /posts/id")
    @Description("GET /posts/id должно вернуть JSON с ответом 200 для существующего поста и 404- для несуществующего")
    void GETPost(){
        GETCorrectPost();
        GETIncorrectPost();
    }

    @Test
    @Order(3)
    @DisplayName("POST /posts")
    @Description("POST /posts должно отправить сообщение и вернуть ответ с id=101 и кодом ответа 201")
    void POSTPosts(){
        fullPost = new FullPostBody(101, 1, "foo", "bar");
        sendPOSTRequest(posts, fullPost);
    }

    @Test
    @Order(4)
    @Severity(SeverityLevel.MINOR)
    @DisplayName("PUT /posts/id")
    @Description("PUT /posts/id должно изменить тело сообщения id=%d и вернуть новое тело сообщения с кодом 200")
    void PUTPost(){
        id = randomCorrectIndex(postsQty);
        checkPost(id, fullPost);
        sendPUTRequest(id, fullPost);
    }

    @Test
    @Order(5)
    @DisplayName("PATCH /posts/id")
    @Description("PATCH /posts/id должен изменить поля userId и title и вернуть тело сообщения с кодом 200")
    void PATCHPost(){
        id = randomCorrectIndex(postsQty);
        partialPost = new PartialPostBody(id, 999, "FOO");
        checkPost(id, fullPost);
        sendPATCHRequest(id, partialPost);
    }

    @Test
    @Order(6)
    @DisplayName("DELETE /posts/id")
    @Description("DELETE /posts/id должен вернуть пустое тело с кодом 200")
    void DELETEPost(){
        id = randomCorrectIndex(postsQty);
        checkPost(id);
        sendDELETERequest(id);
    }
}
