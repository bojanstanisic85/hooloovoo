package rs.hooloovoo.test.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.json.JSONObject;


import java.math.BigInteger;
import java.sql.*;
import java.util.Calendar;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilityMethods {

    public static String generateRandomAlphanumericString(int lenght) {
        return RandomStringUtils.randomAlphanumeric(lenght);
    }

    public static String createJsonForRequestBody(String username, String password) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("password", password);
        jsonObject.put("username", username);
        return jsonObject.toString();
    }

    public static int exstractIntFromString(String s) {
        int n = 0;
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(s);
        while (m.find()) {
            n = Integer.parseInt(m.group());
        }
        return n;
    }

    public static Long generateRandomBigInteger() {
        BigInteger maxLimit = new BigInteger("5000000000000");
        BigInteger minLimit = new BigInteger("25000000000");
        BigInteger bigInteger = maxLimit.subtract(minLimit);
        Random randNum = new Random();
        int len = maxLimit.bitLength();
        BigInteger res = new BigInteger(len, randNum);
        if (res.compareTo(minLimit) < 0)
            res = res.add(minLimit);
        if (res.compareTo(bigInteger) >= 0)
            res = res.mod(bigInteger).add(minLimit);

        return res.longValue();
    }

    public static Timestamp generateCurrentTimeTimestamp() {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();

        return new java.sql.Timestamp(now.getTime());
    }


    public static String getUserActionFromDatabase(int id) throws SQLException {
        String resultSetString = null;
        Connection connection = DriverManager.
                getConnection("jdbc:h2:tcp://localhost:8762/mem:userdb?IFEXISTS=false", "sa", "password");

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT ACTION_TYPE FROM AUDIT_LOG_ENTRIES WHERE ID = ?");
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            resultSetString = resultSet.getString(1);
        }

        preparedStatement.close();
        connection.close();

        return resultSetString;
    }

    public static String encodeInMD5(String string) {
        return DigestUtils.md5Hex(string);
    }

    public static UserComponents writeUserEntryIntoDatabase() throws SQLException {
        Long userId = generateRandomBigInteger();
        Timestamp created_at = generateCurrentTimeTimestamp();
        String username = generateRandomAlphanumericString(10);
        String password = generateRandomAlphanumericString(10);

        Connection connection = DriverManager.
                getConnection("jdbc:h2:tcp://localhost:8762/mem:userdb?IFEXISTS=false", "sa", "password");

        PreparedStatement preparedStatement = connection.
                prepareStatement("INSERT INTO USERS (ID, CREATED_AT, PASSWORD_ENCRYPTED, USERNAME) VALUES (?, ?, ?, ?);");
        preparedStatement.setLong(1, userId);
        preparedStatement.setTimestamp(2, created_at);
        preparedStatement.setString(3, encodeInMD5(password));
        preparedStatement.setString(4, username);

        preparedStatement.execute();

        preparedStatement.close();
        connection.close();
        return new UserComponents(userId, created_at, username, password);
    }

    public static ActionComponents writeUserActionIntoDatabase(Long userId, String actionType) throws SQLException {
        Long actionId = generateRandomBigInteger();
        Timestamp created_at = generateCurrentTimeTimestamp();

        Connection connection = DriverManager.
                getConnection("jdbc:h2:tcp://localhost:8762/mem:userdb?IFEXISTS=false", "sa", "password");

        PreparedStatement preparedStatement = connection.
                prepareStatement("INSERT INTO AUDIT_LOG_ENTRIES (ID, ACTION_TYPE, CREATED_AT, USER_ID) VALUES (?, ?, ?, ?);");
        preparedStatement.setLong(1, actionId);
        preparedStatement.setString(2, actionType);
        preparedStatement.setTimestamp(3, created_at);
        preparedStatement.setLong(4, userId);

        preparedStatement.execute();

        preparedStatement.close();
        connection.close();
        return new ActionComponents(actionId, actionType, created_at, userId);
    }

    public static class UserComponents {
        public final Long userId;
        public final Timestamp created_at;
        public final String username;
        public final String password;

        public UserComponents(Long userId, Timestamp created_at, String username, String password) {
            this.userId = userId;
            this.created_at = created_at;
            this.username = username;
            this.password = password;
        }
    }

    public static class ActionComponents {
        public final Long actionId;
        public final String actionType;
        public final Timestamp created_at;
        public final Long userId;

        public ActionComponents(Long actionId, String actionType, Timestamp created_at, Long userId) {
            this.actionId = actionId;
            this.actionType = actionType;
            this.created_at = created_at;
            this.userId = userId;
        }
    }
}
