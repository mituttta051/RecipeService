package cybercooker.recipeservice;

import cybercooker.recipeservice.entity.Ingredient;
import cybercooker.recipeservice.repository.IngredientRepository;
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
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IngredientRepository ingredientRepository;
    
    private static final Logger log = LoggerFactory.getLogger(RecipeServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RecipeServiceApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        log.info("Creating tables");
        executeSqlFromFile();
        log.info("Tables created");
        runIngredientRepositoryTests();
    }

    private void executeSqlFromFile() throws IOException {
        File file = ResourceUtils.getFile("classpath:db/init.sql");
        String sql = new String(Files.readAllBytes(file.toPath()));
        jdbcTemplate.execute(sql);
    }
    
    private void runIngredientRepositoryTests() {
        log.info("Running IngredientRepository tests");
        try {
            ingredientRepository.save(Ingredient.builder().spaceId(0).name("test").build());
            log.info(ingredientRepository.getById(1, 0).toString());
            ingredientRepository.update(Ingredient.builder().id(1).spaceId(0).name("test2").build());
            log.info(ingredientRepository.getById(1, 0).toString());
            ingredientRepository.delete(1, 0);
            log.info(ingredientRepository.getAllBySpaceId(0).toString());
            ingredientRepository.save(Ingredient.builder().spaceId(1).name("test3").build());
            ingredientRepository.save(Ingredient.builder().spaceId(1).name("test4").build());
            log.info(ingredientRepository.getAllBySpaceId(1).toString());
            log.info(ingredientRepository.getAllBySpaceId(3).toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
