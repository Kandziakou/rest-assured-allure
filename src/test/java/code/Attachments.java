package code;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import data.posts.FullPostBody;
import io.qameta.allure.Attachment;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

import static data.constants.Quantity.postsQty;

public class Attachments {
    String file = "./src/test/resources/json/posts.json";
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonReader reader = null;

    private List<FullPostBody> readPostsFile(){
        Type TYPE = new TypeToken<List<FullPostBody>>() {
        }.getType();
        try {
            reader = new JsonReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert reader != null;
        return gson.fromJson(reader, TYPE);
    }

    protected void attachExpectedResponse(int id){
        if (id < postsQty){
            attachExpectedResponseToCorrectRequest(id);
        }
        else {
            attachExpectedResponseToIncorrectRequest();
        }
    }

    @Attachment(value = "Expected response to correct request is", fileExtension = "json")
    private String attachExpectedResponseToCorrectRequest(int id){
        List<FullPostBody> posts = readPostsFile();
        return gson.toJson(posts.get(id-1));
    }

    @Attachment(value = "Expected response to GET /posts is", fileExtension = "json")
    protected String attachExpectedResponseToGETPosts(){
        List<FullPostBody> posts = readPostsFile();
        return gson.toJson(posts);
    }

    @Attachment(value = "Expected response to incorrect request is:", fileExtension = "json")
    private String attachExpectedResponseToIncorrectRequest(){
        return "{\n\n}";
    }

    @Attachment(value = "Response JSON is", fileExtension = "json")
    protected String attachResponse(ValidatableResponse response){
        return response.extract().response().asPrettyString();
    }

    @Attachment(value = "{value}", fileExtension = "json")
    protected String attachResponse(Response response, String value){
        return response.asPrettyString();
    }

}
