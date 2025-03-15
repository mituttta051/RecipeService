package cybercooker.recipeservice.entity.filter;

import lombok.Builder;

@Builder
public class ContainsTagFilter extends Filter {
    private int tagId;
    private int tagValue;

    public String getSql() {
        return "EXISTS (" +
                "SELECT 1 " +
                "FROM jsonb_array_elements(tags::jsonb) AS tag " +
                "WHERE tag->>'id' = '" + tagId + "' " +
                "AND EXISTS (" +
                "SELECT 1 " +
                "FROM jsonb_array_elements_text(tag->'values') AS value " +
                "WHERE value = '" + tagValue + "'" +
                ")" +
                ")";
    }
}
