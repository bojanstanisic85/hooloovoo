package rs.hooloovoo.test;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.Test;
import rs.hooloovoo.test.parameters.UserLoginDataProvider;

import static com.jayway.restassured.RestAssured.given;
import static rs.hooloovoo.test.utils.UtilityMethods.createJsonForRequestBody;

public class UserLogin extends BaseClass {

    @Test(dataProvider = "UserLoginDataProvider", dataProviderClass = UserLoginDataProvider.class)
    public void userLoginTest(String username, String password, String expectedMessage, int statusCode) throws JSONException {
        Response response = given().
                contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(createJsonForRequestBody(username, password))
                .when()
                .post(BASE_ENDPOINT + "/users/login");

        // verify response status code
        response.then().statusCode(statusCode);

        if (!expectedMessage.isBlank()) {
            Assert.assertEquals(expectedMessage, response.asString());
        }
    }
}
