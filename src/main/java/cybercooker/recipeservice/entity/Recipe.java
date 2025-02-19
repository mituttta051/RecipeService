package cybercooker.recipeservice.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Recipe {
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
}
