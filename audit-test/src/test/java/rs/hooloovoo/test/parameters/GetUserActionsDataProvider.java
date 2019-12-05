package rs.hooloovoo.test.parameters;

import org.testng.annotations.DataProvider;
import rs.hooloovoo.test.utils.UtilityMethods;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.sql.Timestamp;

import static rs.hooloovoo.test.utils.UtilityMethods.*;

public class GetUserActionsDataProvider {
    @DataProvider(name = "GetUserActionsDataProvider")
    public static Object[][] getScenarioData(Method method) throws SQLException {

        //we need to register user before trying to add user actions
        UtilityMethods.UserComponents userComponents = writeUserEntryIntoDatabase();
        String username = userComponents.username;
        Long userId = userComponents.userId;

        //we need to add user actions before getting them, we will test for LOGIN action only, since all is the same for others
        ActionComponents actionComponents = writeUserActionIntoDatabase(userId, "LOGIN");

        //we will use those fields for verification of values from response
        Long actionId = actionComponents.actionId;
        String actionType = actionComponents.actionType;
        Timestamp createdAt = actionComponents.created_at;

        String testCase = method.getName();

        if ("getUserActionsStatusCodeTest".equals(testCase)) {
            return new Object[][]{{username, 200}};
        } else {
            return new Object[][]{{username, actionId, actionType, createdAt}};
        }
    }
}
