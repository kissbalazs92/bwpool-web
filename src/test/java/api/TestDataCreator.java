package api;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Arrays;
import java.util.List;

public class TestDataCreator {

    public <T> List<T> getRandomItems(String url, Class<T[]> clazz) {
        RequestSpecification request = RestAssured.given();
        Response response = request.get(url);
        T[] array = new Gson().fromJson(response.asString(), clazz);
        return Arrays.asList(array);
    }
}
