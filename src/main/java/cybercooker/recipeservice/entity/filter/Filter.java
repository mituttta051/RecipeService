package cybercooker.recipeservice.entity.filter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class Filter {
    public abstract String getSql();

}
