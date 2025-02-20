package cybercooker.recipeservice;


import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@SpringBootTest(classes = RecipeServiceApplication.class)
public abstract class RepositoryTests {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @BeforeAll
    static void init(@Autowired JdbcTemplate jdbcTemplate) throws IOException {
        jdbcTemplate.execute("DROP DATABASE IF EXISTS \"recipe-db-test\"");
        jdbcTemplate.execute("CREATE DATABASE \"recipe-db-test\"");

        DataSource dataSource = new DriverManagerDataSource("jdbc:postgresql://localhost:5432/recipe-db-test", "admin", "secret");
        jdbcTemplate.setDataSource(dataSource);

        File file = ResourceUtils.getFile("classpath:db/init.sql");
        String sql = new String(Files.readAllBytes(file.toPath()));
        jdbcTemplate.execute(sql);
    }
}
