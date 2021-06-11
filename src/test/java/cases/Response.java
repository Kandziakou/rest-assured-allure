package cases;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import data.post.PostBody;
import io.qameta.allure.Attachment;
import io.restassured.response.ValidatableResponse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class Response {
    String file = "./src/test/resources/posts.json";
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonReader reader = null;

    @Attachment(value = "Expected response to correct request is", fileExtension = "json")
    protected String attachExpectedResponseToCorrectRequest(int id){
        Type TYPE = new TypeToken<List<PostBody>>() {
        }.getType();
        try {
            reader = new JsonReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert reader != null;
        List<PostBody> posts = gson.fromJson(reader, TYPE);
        return gson.toJson(posts.get(id-1));
    }

    @Attachment(value = "Expected response to correct request is", fileExtension = "json")
    protected String attachExpectedResponseToCorrectRequest(){
        Type TYPE = new TypeToken<List<PostBody>>() {
        }.getType();
        try {
            reader = new JsonReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert reader != null;
        //return gson.fromJson(reader, TYPE);
        return "";
    }

    @Attachment(value = "Expected response to incorrect request is:", fileExtension = "json")
    protected String attachExpectedResponseToIncorrectRequest(){
        return "{\n\n}";
    }

    @Attachment(value = "Response JSON is", fileExtension = "json")
    protected String attachResponse(ValidatableResponse response){
        return response.extract().response().asPrettyString();
    }
}
