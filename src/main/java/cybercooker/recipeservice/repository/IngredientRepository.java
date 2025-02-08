package cybercooker.recipeservice.repository;

import cybercooker.recipeservice.entity.Ingredient;
import cybercooker.recipeservice.exception.AlreadyExistsException;
import cybercooker.recipeservice.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class IngredientRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Ingredient getById(int id) throws NotFoundException {
        String sql = "SELECT * FROM ingredients WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> Ingredient.builder()
                    .id(rs.getInt("id"))
                    .spaceId(rs.getInt("space_id"))
                    .name(rs.getString("name"))
                    .build());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Ingredient with id " + id + " not found");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Ingredient ingredient) throws AlreadyExistsException {
        String sql = "INSERT INTO ingredients (space_id, name) VALUES (?, ?)";
        try {
            jdbcTemplate.update(sql, ingredient.getSpaceId(), ingredient.getName());
        } catch (DuplicateKeyException e) {
            throw new AlreadyExistsException("Ingredient with name " + ingredient.getName() + " already exists in space " + ingredient.getSpaceId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Ingredient ingredient) throws NotFoundException, AlreadyExistsException {
        String sql = "UPDATE ingredients SET space_id = ?, name = ? WHERE id = ?";
        try {
            getById(ingredient.getId());
            jdbcTemplate.update(sql, ingredient.getSpaceId(), ingredient.getName(), ingredient.getId());
        } catch (NotFoundException e) {
            throw new NotFoundException("Ingredient with id " + ingredient.getId() + " not found");
        } catch (DuplicateKeyException e) {
            throw new AlreadyExistsException("Ingredient with name " + ingredient.getName() + " already exists in space " + ingredient.getSpaceId());
        }
    }

    public void delete(int id) throws NotFoundException {
        String sql = "DELETE FROM ingredients WHERE id = ?";
        try {
            getById(id);
            jdbcTemplate.update(sql, id);
        } catch (NotFoundException e) {
            throw new NotFoundException("Ingredient with id " + id + " not found");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
