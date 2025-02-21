package cybercooker.recipeservice.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Tag implements SpacedEntity {
    private int id;
    private int spaceId;
    private String name;
    private Map<Integer, String> values;

    @Override
    public String toString() {
        return "Tag: " +
                "id=" + id +
                ", spaceId=" + spaceId +
                ", name='" + name + '\'' +
                ", values=" + values;
    }
}
