package cybercooker.recipeservice.request.recipe;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class RecipeCreateRequest {
    @NotNull
    private Integer spaceId;
    @NotNull
    private String name;
    private String description;
    private Integer servingsNumber;
    private Integer cookTime;
    private Integer shelfLife;
    private List<Integer> ingredients;
    private List<Tag> tags;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Tag {
        private Integer id;
        private List<Integer> values;
    }
    
}

