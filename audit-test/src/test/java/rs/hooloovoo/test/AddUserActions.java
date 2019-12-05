package rs.hooloovoo.test;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import rs.hooloovoo.test.parameters.AddUserActionsDataProvider;

import java.sql.*;

import static com.jayway.restassured.RestAssured.given;
import static rs.hooloovoo.test.utils.UtilityMethods.getUserActionFromDatabase;
import static rs.hooloovoo.test.utils.UtilityMethods.exstractIntFromString;

public class AddUserActions extends BaseClass {

    @Test(dataProvider = "AddUserActionsDataProvider", dataProviderClass = AddUserActionsDataProvider.class)
    public void addUserActionsTest(String username, String action, int statusCode) throws SQLException {
        Response response = given().
                contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .param("username", username)
                .param("action", action)
                .when()
                .put(BASE_ENDPOINT + "/audit/add");
        System.out.println("PUT Response\n" + response.asString());
        // Verify status code is correct
        response.then().statusCode(statusCode);

        if (action.equals("LOGIN") || action.equals("SAVE") || action.equals("UPDATE") || action.equals("DELETE")) {
            String responseString = response.getBody().asString();
            //we had to make utility method to extract id from response body since response body is not valid JSON
            int id = exstractIntFromString(responseString);
            //we are getting ACTION from database with id from response, and comparing it with ACTION we have
            Assert.assertEquals(action, getUserActionFromDatabase(id));
        }
    }

}
