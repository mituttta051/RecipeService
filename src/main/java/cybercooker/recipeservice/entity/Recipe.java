package cybercooker.recipeservice.entity;

import com.fasterxml.jackson.databind.JsonNode;
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
    private JsonNode tags;
}
