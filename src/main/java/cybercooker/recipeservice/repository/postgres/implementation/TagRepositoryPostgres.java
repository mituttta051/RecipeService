package cybercooker.recipeservice.repository.postgres.implementation;

import cybercooker.recipeservice.entity.Tag;
import cybercooker.recipeservice.repository.interfaces.TagRepository;
import cybercooker.recipeservice.repository.postgres.PostgresRepository;
import cybercooker.recipeservice.repository.postgres.utils.TagUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class TagRepositoryPostgres extends PostgresRepository<Tag> implements TagRepository {
    @Override
    protected RowMapper<Tag> rowMapper() {
        return (rs, rowNum) -> Tag.builder()
                .id(rs.getInt("id"))
                .spaceId(rs.getInt("space_id"))
                .name(rs.getString("name"))
                .values(TagUtils.hstoreAsMap(rs.getString("values")))
                .build();
    }

    @Override
    protected String getTableName() {
        return "tag";
    }

    @Override
    protected String generateGetByIdQuery() {
        return "SELECT * FROM tag WHERE id = ? AND space_id = ?";
    }

    @Override
    protected String generateGetAllBySpaceIdQuery() {
        return "SELECT * FROM tag WHERE space_id = ?";
    }

    @Override
    protected String generateSaveQuery() {
        return "INSERT INTO tag (space_id, name, values) VALUES (?, ?, ?)";
    }

    @Override
    protected String generateUpdateQuery() {
        return "UPDATE tag SET name = ?, values = ? WHERE id = ? AND space_id = ?";
    }

    @Override
    protected String generateDeleteQuery() {
        return "DELETE FROM tag WHERE id = ? AND space_id = ?";
    }

    @Override
    protected ParamsExtractor<Tag> getSaveParamsExtractor() {
        return tag -> new Object[]{tag.getSpaceId(), tag.getName(), TagUtils.mapAsHstore(tag.getValues())};
    }

    @Override
    protected ParamsExtractor<Tag> getUpdateParamsExtractor() {
        return tag -> new Object[]{tag.getName(), TagUtils.mapAsHstore(tag.getValues()), tag.getId(), tag.getSpaceId()};
    }

}
