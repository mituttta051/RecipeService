package cybercooker.recipeservice;

import cybercooker.recipeservice.grpc.interceptor.GlobalExceptionInterceptor;
import cybercooker.recipeservice.grpc.service.IngredientGrpcService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@SpringBootApplication
public class RecipeServiceApplication implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(RecipeServiceApplication.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RecipeServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        executeSqlFromFile();
    }

    private void executeSqlFromFile() throws IOException {
        log.info("Creating tables");
        
        File file = ResourceUtils.getFile("classpath:db/init.sql");
        String sql = new String(Files.readAllBytes(file.toPath()));
        jdbcTemplate.execute(sql);
        
        log.info("Tables created");
    }
}
