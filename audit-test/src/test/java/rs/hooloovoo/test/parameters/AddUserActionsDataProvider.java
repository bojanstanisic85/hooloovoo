package rs.hooloovoo.test.parameters;

import org.json.JSONException;
import org.testng.annotations.DataProvider;
import rs.hooloovoo.test.utils.UtilityMethods;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static rs.hooloovoo.test.utils.UtilityMethods.writeUserEntryIntoDatabase;

public class AddUserActionsDataProvider {
    @DataProvider(name = "AddUserActionsDataProvider")
    public static Object[][] getDataFromDataProvider() throws JSONException, SQLException, NoSuchAlgorithmException {

        //we need to register user before trying to add user actions
        UtilityMethods.UserComponents userComponents = writeUserEntryIntoDatabase();
        String username = userComponents.username;

        return new Object[][]
                {
                        {username, "LOGIN", 201},
                        {username, "SAVE", 201},
                        {username, "UPDATE", 201},
                        {username, "DELETE", 201},
                        //status 500 is not documented, so we can assume this is not expected behaviour
                        {username, "NonSupportedAction", 500}
                };
    }
}
