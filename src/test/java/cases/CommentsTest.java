package cases;

import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CommentsTest extends BaseTest{
    @Test
    @DisplayName("GET /posts/%d/comments")
    @Description("Ответ GET /posts/%d/comments будет проверен на соответствие схеме")
    void GETCommentsToPost(){

    }

    @Test
    @DisplayName("GET /comments?postId=%d")
    @Description("А ответ GET /comments?postId=%d будет просто проверен на заголовки и код ответа")
    void GETCommentsToPostV2(){

    }

    @Test
    @DisplayName("Final checking GET comments")
    @Description("GET /posts/%d/comments and GET /comments?postId=%d should return same response")
        void TwoEndpointsOneResponse(){

    }
}
