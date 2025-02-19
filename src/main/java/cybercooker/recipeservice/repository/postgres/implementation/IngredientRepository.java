package cybercooker.recipeservice.repository.postgres.implementation;

import cybercooker.recipeservice.entity.Ingredient;
import cybercooker.recipeservice.repository.postgres.PostgresRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class IngredientRepository extends PostgresRepository<Ingredient> {


    @Override
    protected RowMapper<Ingredient> rowMapper() {
        return (rs, rowNum) -> Ingredient.builder()
                .id(rs.getInt("id"))
                .spaceId(rs.getInt("space_id"))
                .name(rs.getString("name"))
                .build();
    }

    @Override
    protected String getTableName() {
        return "ingredient";
    }

    @Override
    protected String generateGetByIdQuery() {
        return "SELECT * FROM ingredient WHERE id = ? AND space_id = ?";
    }

    @Override
    protected String generateGetAllBySpaceIdQuery() {
        return "SELECT * FROM ingredient WHERE space_id = ?";
    }

    @Override
    protected String generateSaveQuery() {
        return "INSERT INTO ingredient (space_id, name) VALUES (?, ?)";
    }

    @Override
    protected String generateUpdateQuery() {
        return "UPDATE ingredient SET name = ? WHERE id = ? AND space_id = ?";
    }

    @Override
    protected String generateDeleteQuery() {
        return "DELETE FROM ingredient WHERE id = ? AND space_id = ?";
    }

    @Override
    protected int getId(Ingredient ingredient) {
        return ingredient.getId();
    }

    @Override
    protected int getSpaceId(Ingredient ingredient) {
        return ingredient.getSpaceId();
    }

    @Override
    protected String getName(Ingredient ingredient) {
        return ingredient.getName();
    }

    @Override
    protected ParamsExtractor<Ingredient> getSaveParamsExtractor() {
        return ingredient -> new Object[]{ingredient.getSpaceId(), ingredient.getName()};
    }

    @Override
    protected ParamsExtractor<Ingredient> getUpdateParamsExtractor() {
        return ingredient -> new Object[]{ingredient.getName(), ingredient.getId(), ingredient.getSpaceId()};
    }
}
