package cybercooker.recipeservice.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Tag {
    private int id;
    private int spaceId;
    private String name;
    private Map<Integer, String> values;
}
