package rs.hooloovoo.test;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import rs.hooloovoo.test.parameters.GetUserDataProvider;

import java.sql.Timestamp;

import static com.jayway.restassured.RestAssured.given;

public class GetUser extends BaseClass {

    @Test(dataProvider = "GetUserDataProvider", dataProviderClass = GetUserDataProvider.class)
    public void getExistingUserVerifyStatusCodeTest(String username, int statusCode) {
        RequestSpecification httpRequest = given();
        Response response = httpRequest.get(BASE_ENDPOINT + "/users/" + username);

        //Validate the returned status code
        response.then().statusCode(statusCode);
    }

    @Test(dataProvider = "GetUserDataProvider", dataProviderClass = GetUserDataProvider.class)
    public void getExistingUserVerifyIdTest(Long id, String username) {
        RequestSpecification httpRequest = given();
        Response response = httpRequest.get(BASE_ENDPOINT + "/users/" + username);

        //Validate the returned userId
        response.then().body("id", Matchers.is(id));
    }

    @Test(dataProvider = "GetUserDataProvider", dataProviderClass = GetUserDataProvider.class)
    public void getExistingUserVerifyUsernameTest(Long id, String username) {
        RequestSpecification httpRequest = given();
        Response response = httpRequest.get(BASE_ENDPOINT + "/users/" + username);

        //Validate the returned username
        response.then().body("username", Matchers.is(username));
    }

    @Test(dataProvider = "GetUserDataProvider", dataProviderClass = GetUserDataProvider.class)
    public void getExistingUserVerifyCreatedAtTest(String username, Timestamp createdAt) {
        RequestSpecification httpRequest = given();
        Response response = httpRequest.get(BASE_ENDPOINT + "/users/" + username);

        //Validate the returned createdAt

        response.then().body("createdAt", Matchers.is(createdAt));
    }

    @Test(dataProvider = "GetUserDataProvider", dataProviderClass = GetUserDataProvider.class)
    public void getNonExistingUserTest(String username, int statusCode) {
        given().when().get(BASE_ENDPOINT + "/users/" + username).then().statusCode(statusCode);
    }
}
