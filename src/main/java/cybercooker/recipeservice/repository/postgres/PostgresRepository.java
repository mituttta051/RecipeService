package cybercooker.recipeservice.repository.postgres;

import cybercooker.recipeservice.entity.SpacedEntity;
import cybercooker.recipeservice.exception.AlreadyExistsException;
import cybercooker.recipeservice.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public abstract class PostgresRepository<T extends SpacedEntity> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    protected abstract RowMapper<T> rowMapper();

    protected abstract String getTableName();

    protected abstract String generateGetByIdQuery();

    protected abstract String generateGetAllBySpaceIdQuery();

    protected abstract String generateSaveQuery();

    protected abstract String generateUpdateQuery();

    protected abstract String generateDeleteQuery();

    protected abstract ParamsExtractor<T> getSaveParamsExtractor();

    protected abstract ParamsExtractor<T> getUpdateParamsExtractor();

    public T getById(int id, int spaceId) throws NotFoundException {
        String sql = generateGetByIdQuery();
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper(), id, spaceId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(getTableName() + " with id " + id + " not found");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<T> getAllBySpaceId(int spaceId) {
        String sql = generateGetAllBySpaceIdQuery();
        return jdbcTemplate.query(sql, rowMapper(), spaceId);
    }

    public void save(T t) throws AlreadyExistsException {
        String sql = generateSaveQuery();
        try {
            Object[] params = getSaveParamsExtractor().extract(t);
            jdbcTemplate.update(sql, params);
        } catch (DuplicateKeyException e) {
            throw new AlreadyExistsException(t.toString() + " already exists in space " + t.getSpaceId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void update(T t) throws NotFoundException, AlreadyExistsException {
        String sql = generateUpdateQuery();
        try {
            int numOfRows = jdbcTemplate.update(sql, getUpdateParamsExtractor().extract(t));
            if (numOfRows == 0) {
                throw new NotFoundException(getTableName() + " with id " + t.getId() + " not found");
            }
        } catch (DuplicateKeyException e) {
            throw new AlreadyExistsException(t.toString() + " already exists in space " + t.getSpaceId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void delete(int id, int spaceId) throws NotFoundException {
        String sql = generateDeleteQuery();
        int numOfRows = jdbcTemplate.update(sql, id, spaceId);
        if (numOfRows == 0) {
            throw new NotFoundException(getTableName() + " with id " + id + " not found");
        }
    }

    @FunctionalInterface
    protected interface ParamsExtractor<T> {
        Object[] extract(T entity) throws SQLException;
    }
}
