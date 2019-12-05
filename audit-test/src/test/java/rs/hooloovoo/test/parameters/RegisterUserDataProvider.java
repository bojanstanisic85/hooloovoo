package rs.hooloovoo.test.parameters;

import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;

import static rs.hooloovoo.test.utils.UtilityMethods.generateRandomAlphanumericString;

public class RegisterUserDataProvider {
    static final String s_username = generateRandomAlphanumericString(6);
    static final String s_username1 = generateRandomAlphanumericString(6);

    @DataProvider(name = "RegisterUserDataProvider")
    public static Object[][] getScenarioData(Method method) {

        String username = s_username;
        String username1 = s_username1;

        String password = generateRandomAlphanumericString(6);
        //username should be in range from 6 to 50 characters per documentation, but it is from 5 to 50, so this is bug
        String shortUsername = generateRandomAlphanumericString(4);
        String longUsername = generateRandomAlphanumericString(51);

        String testCase = method.getName();

        if ("registerUserStatusCodeTest".equals(testCase)) {
            return new Object[][]{{username, password, 201}};
        } else if ("registerUserVerifyUsernameFromBodyTest".equals(testCase)) {
            return new Object[][]{{username1, password}};
        } else if ("registerUserWithShortUsernameTest".equals(testCase)) {
            return new Object[][]{{shortUsername, password, "Username must not be longer that 50 characters or shorter than 6 characters.", 400}};
        } else if ("registerUserWithLongUsernameTest".equals(testCase)) {
            return new Object[][]{{longUsername, password, "Username must not be longer that 50 characters or shorter than 6 characters.", 400}};
        } else {
            return new Object[][]{{username1, password, "User with the specified username already exists!", 400}};
        }
    }
}
