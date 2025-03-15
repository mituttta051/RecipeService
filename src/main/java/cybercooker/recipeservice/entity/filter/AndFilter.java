package cybercooker.recipeservice.entity.filter;

import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class AndFilter extends Filter {
    private List<Filter> filters;

    public String getSql() {
        return filters.stream()
                .map(Filter::getSql)
                .collect(Collectors.joining(" AND "));
    }

}
