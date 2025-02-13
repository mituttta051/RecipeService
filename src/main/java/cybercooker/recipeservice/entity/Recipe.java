package cybercooker.recipeservice.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Recipe {
    private int id;
    int spaceId;
    String name;
    String description;
    List<Integer> ingredients;
    int servingsNumber;
    int cookTime;
    private List<Property> tags;
}
