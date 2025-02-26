package cybercooker.recipeservice.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Ingredient implements SpacedEntity {
    private int id;
    private int spaceId;
    private String name;

    @Override
    public String toString() {
        return "Ingredient: " +
                "id=" + id +
                ", spaceId=" + spaceId +
                ", name='" + name + '\'';
    }
}
