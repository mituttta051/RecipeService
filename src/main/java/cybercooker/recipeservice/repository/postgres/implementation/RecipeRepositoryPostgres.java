package cybercooker.recipeservice.repository.postgres.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cybercooker.recipeservice.entity.Recipe;
import cybercooker.recipeservice.entity.filter.Filter;
import cybercooker.recipeservice.repository.interfaces.RecipeRepository;
import cybercooker.recipeservice.repository.postgres.PostgresRepository;
import cybercooker.recipeservice.repository.postgres.utils.ListUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecipeRepositoryPostgres extends PostgresRepository<Recipe> implements RecipeRepository {

    @Override
    protected RowMapper<Recipe> rowMapper() {
        return (rs, rowNum) -> {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Recipe.Tag> tags;
            try {
                tags = objectMapper.readValue(rs.getString("tags"), new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            return Recipe.builder()
                    .id(rs.getInt("id"))
                    .spaceId(rs.getInt("space_id"))
                    .name(rs.getString("name"))
                    .description(rs.getString("description"))
                    .ingredients(ListUtils.sqlArrayToList(rs.getArray("ingredients")))
                    .servingsNumber(rs.getInt("servings_number"))
                    .cookTime(rs.getInt("cook_time"))
                    .tags(tags)
                    .build();
        };
    }

    @Override
    protected String getTableName() {
        return "recipe";
    }

    @Override
    protected String generateGetByIdQuery() {
        return "SELECT * FROM recipe WHERE id = ? AND space_id = ?";
    }

    @Override
    protected String generateGetAllBySpaceIdQuery() {
        return "SELECT * FROM recipe WHERE space_id = ?";
    }

    @Override
    protected String generateSaveQuery() {
        return "INSERT INTO recipe (space_id, name, description, ingredients, servings_number, cook_time, tags) VALUES (?, ?, ?, ?, ?, ?, ?::jsonb)";
    }

    @Override
    protected String generateUpdateQuery() {
        return "UPDATE recipe SET name = ?, description = ?, ingredients = ?, servings_number = ?, cook_time = ?, tags = ?::jsonb  WHERE id = ? AND space_id = ?";
    }

    @Override
    protected String generateDeleteQuery() {
        return "DELETE FROM recipe WHERE id = ? AND space_id = ?";
    }

    @Override
    protected ParamsExtractor<Recipe> getSaveParamsExtractor() {
        return recipe -> {
            ObjectMapper objectMapper = new ObjectMapper();
            String tags;
            try {
                tags = objectMapper.writeValueAsString(recipe.getTags());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            return new Object[]{
                    recipe.getSpaceId(),
                    recipe.getName(),
                    recipe.getDescription(),
                    recipe.getIngredients().toArray(new Integer[0]),
                    recipe.getServingsNumber(),
                    recipe.getCookTime(),
                    tags
            };
        };
    }

    @Override
    protected ParamsExtractor<Recipe> getUpdateParamsExtractor() {
        return recipe -> {
            ObjectMapper objectMapper = new ObjectMapper();
            String tags;
            try {
                tags = objectMapper.writeValueAsString(recipe.getTags());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return new Object[]{
                    recipe.getName(),
                    recipe.getDescription(),
                    recipe.getIngredients().toArray(new Integer[0]),
                    recipe.getServingsNumber(),
                    recipe.getCookTime(),
                    tags,
                    recipe.getId(),
                    recipe.getSpaceId()};
        };
    }

    @Override
    public List<Recipe> getRecipesByFilter(Filter filter, int spaceId) {
        String sql = "SELECT * FROM RECIPE WHERE space_id = " + spaceId + " AND " + filter.getSql();
        return jdbcTemplate.query(sql, rowMapper());
    }
}
