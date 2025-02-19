package cybercooker.recipeservice;

import cybercooker.recipeservice.entity.Ingredient;
import cybercooker.recipeservice.exception.AlreadyExistsException;
import cybercooker.recipeservice.exception.NotFoundException;
import cybercooker.recipeservice.repository.postgres.implementation.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(classes = RecipeServiceApplication.class)
public class IngredientRepositoryTests {
    @Autowired private IngredientRepository ingredientRepository;
    @Autowired private JdbcTemplate jdbcTemplate;
    
    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM ingredient");
        jdbcTemplate.execute("DELETE FROM last_ingredient_id");
    }
    
    @Test
    void testSave() {
        Ingredient ingredient = Ingredient.builder()
                .id(1)
                .spaceId(1)
                .name("Sugar")
                .build();
        ingredientRepository.save(ingredient);
        Ingredient savedIngredient = ingredientRepository.getById(1, 1);
        assertThat(savedIngredient).isEqualTo(ingredient);
        
        Ingredient ingredient2 = Ingredient.builder()
                .id(2)
                .spaceId(1)
                .name("Salt")
                .build();
        ingredientRepository.save(ingredient2);
        Ingredient savedIngredient2 = ingredientRepository.getById(2, 1);
        assertThat(savedIngredient2).isEqualTo(ingredient2);
        
        Ingredient ingredient3 = Ingredient.builder()
                .id(1)
                .spaceId(2)
                .name("Salt")
                .build();
        ingredientRepository.save(ingredient3);
        Ingredient savedIngredient3 = ingredientRepository.getById(1, 2);
        assertThat(savedIngredient3).isEqualTo(ingredient3);
        
    }
    
    @Test
    void testSaveThatAlreadyExists() {
        Ingredient ingredient = Ingredient.builder()
                .id(1)
                .spaceId(1)
                .name("Salt")
                .build();
        ingredientRepository.save(ingredient);
        Ingredient savedIngredient = ingredientRepository.getById(1, 1);
        assertThat(savedIngredient).isEqualTo(ingredient);
        
        Ingredient ingredient2 = Ingredient.builder()
                .spaceId(1)
                .name("Salt")
                .build();
        assertThrows(AlreadyExistsException.class, () -> ingredientRepository.save(ingredient2));
    }
    
    @Test
    void testGetByIdThatDoesNotExist() {
        assertThrows(NotFoundException.class, () -> ingredientRepository.getById(1, 1));
    }
    
    @Test
    void TestGetAllBySpaceId() {
        Ingredient ingredient = Ingredient.builder()
                .id(1)
                .spaceId(1)
                .name("Sugar")
                .build();
        ingredientRepository.save(ingredient);
        
        Ingredient ingredient2 = Ingredient.builder()
                .id(2)
                .spaceId(1)
                .name("Salt")
                .build();
        ingredientRepository.save(ingredient2);
        
        Ingredient ingredient3 = Ingredient.builder()
                .id(1)
                .spaceId(2)
                .name("Salt")
                .build();
        ingredientRepository.save(ingredient3);
        
        assertThat(ingredientRepository.getAllBySpaceId(1)).containsExactlyInAnyOrder(ingredient, ingredient2);
        assertThat(ingredientRepository.getAllBySpaceId(2)).containsExactlyInAnyOrder(ingredient3);
        assertThat(ingredientRepository.getAllBySpaceId(3)).isEmpty();
    }
    
    @Test
    void TestUpdate() {
        Ingredient ingredient = Ingredient.builder()
                .spaceId(1)
                .name("Sugar")
                .build();
        ingredientRepository.save(ingredient);
        
        Ingredient updatedIngredient = Ingredient.builder()
                .id(1)
                .spaceId(1)
                .name("Salt")
                .build();
        ingredientRepository.update(updatedIngredient);
        
        Ingredient savedIngredient = ingredientRepository.getById(1, 1);
        assertThat(savedIngredient).isEqualTo(updatedIngredient);
    }
    
    @Test
    void TestUpdateThatDoesNotExist() {
        Ingredient ingredient = Ingredient.builder()
                .id(1)
                .spaceId(1)
                .name("Sugar")
                .build();
        assertThrows(NotFoundException.class, () -> ingredientRepository.update(ingredient));
    }
    
    @Test
    void TestUpdateThatAlreadyExists() {
        Ingredient ingredient = Ingredient.builder()
                .spaceId(1)
                .name("Sugar")
                .build();
        ingredientRepository.save(ingredient);
        
        Ingredient ingredient2 = Ingredient.builder()
                .spaceId(1)
                .name("Salt")
                .build();
        ingredientRepository.save(ingredient2);
        
        Ingredient updatedIngredient = Ingredient.builder()
                .id(1)
                .spaceId(1)
                .name("Salt")
                .build();
        assertThrows(AlreadyExistsException.class, () -> ingredientRepository.update(updatedIngredient));
    }
    
    @Test
    void TestDelete() {
        Ingredient ingredient = Ingredient.builder()
                .spaceId(1)
                .name("Sugar")
                .build();
        ingredientRepository.save(ingredient);

        ingredientRepository.delete(1, 1);
        assertThrows(NotFoundException.class, () -> ingredientRepository.getById(1, 1));
    }
    
    @Test
    void TestDeleteThatDoesNotExist() {
        assertThrows(NotFoundException.class, () -> ingredientRepository.delete(1, 1));
    }
}
