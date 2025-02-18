package cybercooker.recipeservice.repository;

import cybercooker.recipeservice.entity.Ingredient;
import cybercooker.recipeservice.exception.AlreadyExistsException;
import cybercooker.recipeservice.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IngredientRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Ingredient getById(int id, int spaceId) throws NotFoundException {
        String sql = "SELECT * FROM ingredient WHERE id = ? AND space_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> Ingredient.builder()
                    .id(rs.getInt("id"))
                    .spaceId(rs.getInt("space_id"))
                    .name(rs.getString("name"))
                    .build(), id, spaceId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Ingredient with id " + id + " not found");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Ingredient> getAllBySpaceId(int spaceId) throws NotFoundException {
        String sql = "SELECT * FROM ingredient WHERE space_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> Ingredient.builder()
                .id(rs.getInt("id"))
                .spaceId(rs.getInt("space_id"))
                .name(rs.getString("name"))
                .build(), spaceId);
    }

    public void save(Ingredient ingredient) throws AlreadyExistsException {
        String sql = "INSERT INTO ingredient (space_id, name) VALUES (?, ?)";
        try {
            jdbcTemplate.update(sql, ingredient.getSpaceId(), ingredient.getName());
        } catch (DuplicateKeyException e) {
            throw new AlreadyExistsException("Ingredient with name " + ingredient.getName() + " already exists in space " + ingredient.getSpaceId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Ingredient ingredient) throws NotFoundException, AlreadyExistsException {
        String sql = "UPDATE ingredient SET space_id = ?, name = ? WHERE id = ?";
        try {
            int numOfRows = jdbcTemplate.update(sql, ingredient.getSpaceId(), ingredient.getName(), ingredient.getId());
            if (numOfRows == 0) {
                throw new NotFoundException("Ingredient with id " + ingredient.getId() + " not found");
            }
        } catch (DuplicateKeyException e) {
            throw new AlreadyExistsException("Ingredient with name " + ingredient.getName() + " already exists in space " + ingredient.getSpaceId());
        }
    }

    public void delete(int id, int spaceId) throws NotFoundException {
        String sql = "DELETE FROM ingredient WHERE id = ? AND space_id = ?";
        try {
            int numOfRows = jdbcTemplate.update(sql, id, spaceId);
            if (numOfRows == 0) {
                throw new NotFoundException("Ingredient with id " + id + " not found");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
