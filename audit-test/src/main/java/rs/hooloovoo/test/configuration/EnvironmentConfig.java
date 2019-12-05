package rs.hooloovoo.test.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;


@PropertySource("application.properties")
public class EnvironmentConfig {

    @Value("${payment.db.url}")
    private String dbUrl;

    public String getDbUrl() {
        return dbUrl;
    }
}
