package cybercooker.recipeservice.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Property {
    private Integer id;
    private List<Integer> values;
}
