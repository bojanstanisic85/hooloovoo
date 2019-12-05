package rs.hooloovoo.test;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.Test;
import rs.hooloovoo.test.parameters.RegisterUserDataProvider;

import static com.jayway.restassured.RestAssured.given;
import static rs.hooloovoo.test.utils.UtilityMethods.createJsonForRequestBody;

public class RegisterUser extends BaseClass {

    @Test(dataProvider = "RegisterUserDataProvider", dataProviderClass = RegisterUserDataProvider.class)
    public void registerUserStatusCodeTest(String username, String password, int statusCode) throws JSONException {
        Response response = given().
                contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(createJsonForRequestBody(username, password))
                .when()
                .put(BASE_ENDPOINT + "/users/register/");
        System.out.println("PUT Response\n" + response.asString());
        // Verify status code as expected
        response.then().statusCode(statusCode);
    }

    @Test(dataProvider = "RegisterUserDataProvider", dataProviderClass = RegisterUserDataProvider.class)
    public void registerUserVerifyUsernameFromBodyTest(String username, String password) throws JSONException {
        Response response = given().
                contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(createJsonForRequestBody(username, password))
                .when()
                .put(BASE_ENDPOINT + "/users/register/");
        System.out.println("PUT Response\n" + response.asString());
        // Verify username from response body as expected
        response.then().body("username", Matchers.is(username));
    }

    @Test(dataProvider = "RegisterUserDataProvider", dataProviderClass = RegisterUserDataProvider.class)
    public void registerUserWithShortUsernameTest(String username, String password, String expectedMessage, int statusCode) throws JSONException {
        Response response = given().
                contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(createJsonForRequestBody(username, password))
                .when()
                .put(BASE_ENDPOINT + "/users/register/");
        System.out.println("PUT Response\n" + response.asString());
        // Verify status code as expected
        response.then().statusCode(statusCode);
        // Verify message as expected
        Assert.assertEquals(expectedMessage, response.asString());
    }

    @Test(dataProvider = "RegisterUserDataProvider", dataProviderClass = RegisterUserDataProvider.class)
    public void registerUserWithLongUsernameTest(String username, String password, String expectedMessage, int statusCode) throws JSONException {
        Response response = given().
                contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(createJsonForRequestBody(username, password))
                .when()
                .put(BASE_ENDPOINT + "/users/register/");
        System.out.println("PUT Response\n" + response.asString());
        // Verify status code as expected
        response.then().statusCode(statusCode);
        // Verify message as expected
        Assert.assertEquals(expectedMessage, response.asString());
    }

    @Test(dataProvider = "RegisterUserDataProvider", dataProviderClass = RegisterUserDataProvider.class)
    public void registerUserWithExistingNameTest(String username, String password, String expectedMessage, int statusCode) throws JSONException {
        Response response = given().
                contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(createJsonForRequestBody(username, password))
                .when()
                .put(BASE_ENDPOINT + "/users/register/");
        System.out.println("PUT Response\n" + response.asString());
        // Verify status code as expected
        response.then().statusCode(statusCode);
        // Verify message as expected
        Assert.assertEquals(expectedMessage, response.asString());
    }

    /*NOTE:
    Password is not being checked at all, even empty password is allowed - and this is bug.
    Test for checking valid password would be the same as registerUserStatusCodeTest*/
}
