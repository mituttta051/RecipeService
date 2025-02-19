package cybercooker.recipeservice.repository.postgres;

import cybercooker.recipeservice.exception.AlreadyExistsException;
import cybercooker.recipeservice.exception.NotFoundException;
import cybercooker.recipeservice.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

@org.springframework.stereotype.Repository
public abstract class PostgresRepository<T> extends Repository<T> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    protected abstract RowMapper<T> rowMapper();

    protected abstract String getTableName();

    protected abstract String generateGetByIdQuery();

    protected abstract String generateGetAllBySpaceIdQuery();

    protected abstract String generateSaveQuery();

    protected abstract String generateUpdateQuery();

    protected abstract String generateDeleteQuery();

    protected abstract int getId(T t);

    protected abstract int getSpaceId(T t);

    protected abstract String getName(T t);

    protected abstract ParamsExtractor<T> getSaveParamsExtractor();

    protected abstract ParamsExtractor<T> getUpdateParamsExtractor();

    @Override
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

    @Override
    public List<T> getAllBySpaceId(int spaceId) throws NotFoundException {
        String sql = generateGetAllBySpaceIdQuery();
        return jdbcTemplate.query(sql, rowMapper(), spaceId);
    }

    @Override
    public void save(T t) throws AlreadyExistsException {
        String sql = generateSaveQuery();
        Object[] params = getSaveParamsExtractor().extract(t);
        try {
            jdbcTemplate.update(sql, params);
        } catch (DuplicateKeyException e) {
            throw new AlreadyExistsException(getTableName() + " with name " + getName(t) + " already exists in space " + getSpaceId(t));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(T t) throws NotFoundException, AlreadyExistsException {
        String sql = generateUpdateQuery();
        try {
            int numOfRows = jdbcTemplate.update(sql, getUpdateParamsExtractor().extract(t));
            if (numOfRows == 0) {
                throw new NotFoundException(getTableName() + " with id " + getId(t) + " not found");
            }
        } catch (DuplicateKeyException e) {
            throw new AlreadyExistsException(getTableName() + " with name " + getName(t) + " already exists in space " + getSpaceId(t));
        }

    }

    @Override
    public void delete(int id, int spaceId) throws NotFoundException {
        String sql = generateDeleteQuery();
        try {
            int numOfRows = jdbcTemplate.update(sql, id, spaceId);
            if (numOfRows == 0) {
                throw new NotFoundException(getTableName() + " with id " + id + " not found");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @FunctionalInterface
    protected interface ParamsExtractor<T> {
        Object[] extract(T entity);
    }
}
