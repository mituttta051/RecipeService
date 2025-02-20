package cybercooker.recipeservice.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Recipe implements SpacedEntity {
    private int id;
    private int spaceId;
    private String name;
    private String description;
    private List<Integer> ingredients;
    private int servingsNumber;
    private int cookTime;
    private List<Tag> tags;

    @Data
    @Builder
    static class Tag {
        private Integer id;
        private List<Integer> values;
    }
    
    @Override
    public String toString() {
        return "Recipe: " +
                "id=" + id +
                ", spaceId=" + spaceId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ingredients=" + ingredients +
                ", servingsNumber=" + servingsNumber +
                ", cookTime=" + cookTime +
                ", tags=" + tags;
    }
}
