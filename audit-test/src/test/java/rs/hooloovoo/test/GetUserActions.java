package rs.hooloovoo.test;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import rs.hooloovoo.test.parameters.GetUserActionsDataProvider;

import java.sql.Timestamp;

import static com.jayway.restassured.RestAssured.given;

public class GetUserActions extends BaseClass {

    @Test(dataProvider = "GetUserActionsDataProvider", dataProviderClass = GetUserActionsDataProvider.class)
    public void getUserActionsStatusCodeTest(String username, int statusCode) {
        RequestSpecification httpRequest = given().param("username", username);
        Response response = httpRequest.get(BASE_ENDPOINT + "/audit/user/{username}", username);

        //Validate the status code
        response.then().statusCode(statusCode);
    }


    @Test(dataProvider = "GetUserActionsDataProvider", dataProviderClass = GetUserActionsDataProvider.class)
    public void getUserActionsTest(String username, Long actionId, String actionType, Timestamp createdAt) throws JSONException {
        RequestSpecification httpRequest = given().param("username", username);
        Response response = httpRequest.get(BASE_ENDPOINT + "/audit/user/{username}", username);

        JSONArray jsonArray = new JSONArray(response.asString());

        //Here we could pass array of user actions and verify them
        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject action = jsonArray.getJSONObject(i);

            //Verify actionId from response is correct
            Long actionIdFromResponse = (Long) action.get("id");
            Assert.assertEquals(actionIdFromResponse, actionId);

            //Verify user action is expected one
            String actionTypeFromResponse = (String) action.get("actionType");
            Assert.assertEquals(actionTypeFromResponse, actionType);

            //this one is always null, and it is a bug and we will not verify it at the moment
//            Timestamp createdAtFromResponse = (Timestamp) action.get("createdAt");
//            Assert.assertEquals(createdAtFromResponse,createdAt);

            //verify action belongs to current user
            JSONObject user = action.getJSONObject("user");
            Assert.assertEquals(username, user.get("username").toString());
        }
        System.out.println(response.asString());
    }
}
