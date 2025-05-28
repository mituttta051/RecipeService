package cybercooker.recipeservice.request.tag;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import java.util.Map;

@Data
@Builder
public class UpdateTagRequest {
    @NotNull
    private Integer id;
    @NotNull
    private Integer spaceId;
    @NotNull
    private String name;
    private Map<Integer, String> values;
}

