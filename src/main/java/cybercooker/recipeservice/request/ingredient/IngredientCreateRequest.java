package cybercooker.recipeservice.request.ingredient;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IngredientCreateRequest {
    @NotNull
    private Integer spaceId;
    @NotNull
    private String name;
}
