package cases;

import com.google.gson.Gson;
import data.post.PostJson;
import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class PostsTest extends BaseTest{
    private static PostJson pj;

    @BeforeAll
    static void setUp(){
        pj = new PostJson();
    }

    @Test
    @DisplayName("GET /posts")
    @Description("GET /posts должно вернуть JSON c ответом 200")
    void GETPosts(){
        GETAllPosts();
    }

    @Test
    @DisplayName("GET /posts/%d")
    @Description("GET /posts/%d должно вернуть JSON с ответом 200 для существующего поста и 404- для несуществующего")
    void GETPost(){
        GETCorrectPost();
        GETIncorrectPost();
    }

    @Test
    @DisplayName("POST /posts")
    @Description("POST /posts должно отправить сообщение")
    void POSTPosts(){
        Gson gson = new Gson();
        gson.toJson(pj.contentType);
        given().baseUri(URI).body(gson.toJson(pj.body))
                .when().post(posts)
                .then().assertThat().statusCode(201);
    }

    @Test
    @DisplayName("")
    @Description("")
    void PUTPost(){

    }

    @Test
    @DisplayName("")
    @Description()
    void PATCHPost(){

    }

    @Test
    @DisplayName("")
    @Description("")
    void DELETEPost(){

    }
}
