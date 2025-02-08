package cybercooker.recipeservice.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class Tag {
    private int id;
    private int spaceId;
    private String name;
    private List<String> values;
}
