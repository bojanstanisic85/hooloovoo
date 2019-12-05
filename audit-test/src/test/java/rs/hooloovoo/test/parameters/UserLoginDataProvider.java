package rs.hooloovoo.test.parameters;

import org.testng.annotations.DataProvider;
import rs.hooloovoo.test.utils.UtilityMethods;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static rs.hooloovoo.test.utils.UtilityMethods.*;

public class UserLoginDataProvider {

    @DataProvider(name = "UserLoginDataProvider")
    public static Object[][] getDataFromDataProvider() throws SQLException, NoSuchAlgorithmException {

        //we need to register user before trying to login
        UtilityMethods.UserComponents userComponents = writeUserEntryIntoDatabase();
        String username = userComponents.username;
        String password = userComponents.password;
        String nonExistingUsername = generateRandomAlphanumericString(10);
        String wrongPassword = generateRandomAlphanumericString(10);

        return new Object[][]

                {
                        {username, password, "", 200},
                        {nonExistingUsername, password, "Login failed.", 417},
                        {username, "", "Login failed.", 417},
                        {username, wrongPassword, "Login failed.", 417}
                };
    }
}
