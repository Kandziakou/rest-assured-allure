import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class PostsTest extends BaseTest{

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
}
