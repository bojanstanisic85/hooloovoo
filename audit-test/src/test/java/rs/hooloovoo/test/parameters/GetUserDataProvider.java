package rs.hooloovoo.test.parameters;

import org.testng.annotations.DataProvider;
import rs.hooloovoo.test.utils.UtilityMethods;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.sql.Timestamp;

import static rs.hooloovoo.test.utils.UtilityMethods.*;

public class GetUserDataProvider {
    @DataProvider(name = "GetUserDataProvider")
    public static Object[][] getScenarioData(Method method) throws SQLException {

        //we need to register user before trying to add user actions
        UtilityMethods.UserComponents userComponents = writeUserEntryIntoDatabase();
        Long id = userComponents.userId;
        Timestamp createdAt = userComponents.created_at;
        String username = userComponents.username;
        String nonExistingUsername = generateRandomAlphanumericString(10);

        String testCase = method.getName();

        if ("getExistingUserVerifyStatusCodeTest".equals(testCase)) {
            return new Object[][]{{username, 200}};
        } else if ("getExistingUserVerifyIdTest".equals(testCase)) {
            return new Object[][]{{id, username}};
        } else if ("getExistingUserVerifyUsernameTest".equals(testCase)) {
            return new Object[][]{{id, username}};
        } else if ("getExistingUserVerifyCreatedAtTest".equals(testCase)) {
            return new Object[][]{{username, createdAt}};
        } else {
            return new Object[][]{{nonExistingUsername, 404}};
        }

    }
}
