package rs.hooloovoo.test;

import org.junit.Test;

import java.sql.*;

public class UsersTest {
    @Test
    public void testUsers() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost:8762/mem:userdb?IFEXISTS=false", "sa", "password");

        PreparedStatement preparedStatement = connection.prepareStatement("select USERNAME from USERS");

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }

        preparedStatement.close();
        connection.close();
    }
}
