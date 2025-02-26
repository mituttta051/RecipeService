package cybercooker.recipeservice;

import cybercooker.recipeservice.entity.Recipe;
import cybercooker.recipeservice.exception.AlreadyExistsException;
import cybercooker.recipeservice.exception.NotFoundException;
import cybercooker.recipeservice.repository.postgres.implementation.RecipeRepositoryPostgres;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class RecipeRepositoryTests extends RepositoryTests {
    @Autowired
    private RecipeRepositoryPostgres recipeRepository;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM recipe");
        jdbcTemplate.execute("DELETE FROM last_recipe_id");
    }

    @Test
    void testSave() {
        Recipe recipe = Recipe.builder()
                .id(1)
                .spaceId(1)
                .name("Lazagna")
                .description("Delicious")
                .ingredients(List.of(1, 2, 3))
                .servingsNumber(4)
                .cookTime(60)
                .tags(List.of(Recipe.Tag.builder().id(1).values(List.of(1, 2, 3)).build()))
                .build();
        recipeRepository.save(recipe);
        Recipe savedRecipe = recipeRepository.getById(1, 1);
        assertThat(savedRecipe).isEqualTo(recipe);

        Recipe recipe2 = Recipe.builder()
                .id(2)
                .spaceId(1)
                .name("Salt")
                .description("Salty")
                .ingredients(List.of(1))
                .servingsNumber(1)
                .cookTime(0)
                .tags(List.of(Recipe.Tag.builder().id(1).values(List.of(1, 2, 3)).build()))
                .build();

        Recipe recipe3 = Recipe.builder()
                .id(1)
                .spaceId(2)
                .name("Salt")
                .description("Salty")
                .ingredients(List.of(1))
                .servingsNumber(1)
                .cookTime(0)
                .tags(List.of(Recipe.Tag.builder().id(1).values(List.of(1, 2, 3)).build()))
                .build();
        recipeRepository.save(recipe3);
        Recipe savedRecipe3 = recipeRepository.getById(1, 2);
        assertThat(savedRecipe3).isEqualTo(recipe3);
    }

    @Test
    void testSaveThatAlreadyExists() {
        Recipe recipe = Recipe.builder()
                .id(1)
                .spaceId(1)
                .name("Lazagna")
                .description("Delicious")
                .ingredients(List.of(1, 2, 3))
                .servingsNumber(4)
                .cookTime(60)
                .tags(List.of(Recipe.Tag.builder().id(1).values(List.of(1, 2, 3)).build()))
                .build();
        recipeRepository.save(recipe);
        assertThrows(AlreadyExistsException.class, () -> recipeRepository.save(recipe));
    }

    @Test
    void testUpdate() {
        Recipe recipe = Recipe.builder()
                .id(1)
                .spaceId(1)
                .name("Lazagna")
                .description("Delicious")
                .ingredients(List.of(1, 2, 3))
                .servingsNumber(4)
                .cookTime(60)
                .tags(List.of(Recipe.Tag.builder().id(1).values(List.of(1, 2, 3)).build()))
                .build();
        recipeRepository.save(recipe);
        Recipe updatedRecipe = Recipe.builder()
                .id(1)
                .spaceId(1)
                .name("Salt")
                .description("Salty")
                .ingredients(List.of(1))
                .servingsNumber(1)
                .cookTime(0)
                .tags(List.of(Recipe.Tag.builder().id(1).values(List.of(1, 2, 3)).build()))
                .build();
        recipeRepository.update(updatedRecipe);
        Recipe savedRecipe = recipeRepository.getById(1, 1);
        assertThat(savedRecipe).isEqualTo(updatedRecipe);
    }

    @Test
    void testUpdateThatDoesNotExist() {
        Recipe recipe = Recipe.builder()
                .id(1)
                .spaceId(1)
                .name("Lazagna")
                .description("Delicious")
                .ingredients(List.of(1, 2, 3))
                .servingsNumber(4)
                .cookTime(60)
                .tags(List.of(Recipe.Tag.builder().id(1).values(List.of(1, 2, 3)).build()))
                .build();
        assertThrows(NotFoundException.class, () -> recipeRepository.update(recipe));
    }

    @Test
    void testGetByIdThatDoesNotExist() {
        assertThrows(NotFoundException.class, () -> recipeRepository.getById(1, 1));
    }

    @Test
    void testGetAllBySpaceId() {
        Recipe recipe = Recipe.builder()
                .id(1)
                .spaceId(1)
                .name("Lazagna")
                .description("Delicious")
                .ingredients(List.of(1, 2, 3))
                .servingsNumber(4)
                .cookTime(60)
                .tags(List.of(Recipe.Tag.builder().id(1).values(List.of(1, 2, 3)).build()))
                .build();
        recipeRepository.save(recipe);

        Recipe recipe2 = Recipe.builder()
                .id(2)
                .spaceId(1)
                .name("Salt")
                .description("Salty")
                .ingredients(List.of(1))
                .servingsNumber(1)
                .cookTime(0)
                .tags(List.of(Recipe.Tag.builder().id(1).values(List.of(1, 2, 3)).build()))
                .build();
        recipeRepository.save(recipe2);

        Recipe recipe3 = Recipe.builder()
                .id(1)
                .spaceId(2)
                .name("Salt")
                .description("Salty")
                .ingredients(List.of(1))
                .servingsNumber(1)
                .cookTime(0)
                .tags(List.of(Recipe.Tag.builder().id(1).values(List.of(1, 2, 3)).build()))
                .build();
        recipeRepository.save(recipe3);

        assertThat(recipeRepository.getAllBySpaceId(1)).containsExactlyInAnyOrder(recipe, recipe2);
        assertThat(recipeRepository.getAllBySpaceId(2)).containsExactlyInAnyOrder(recipe3);
        assertThat(recipeRepository.getAllBySpaceId(3)).isEmpty();
    }

    @Test
    void testDelete() {
        Recipe recipe = Recipe.builder()
                .id(1)
                .spaceId(1)
                .name("Lazagna")
                .description("Delicious")
                .ingredients(List.of(1, 2, 3))
                .servingsNumber(4)
                .cookTime(60)
                .tags(List.of(Recipe.Tag.builder().id(1).values(List.of(1, 2, 3)).build()))
                .build();
        recipeRepository.save(recipe);
        recipeRepository.delete(1, 1);
        assertThrows(NotFoundException.class, () -> recipeRepository.getById(1, 1));
    }

    @Test
    void testDeleteThatDoesNotExist() {
        assertThrows(NotFoundException.class, () -> recipeRepository.delete(1, 1));
    }
    
}
