package cybercooker.recipeservice.entity.filter;

import lombok.Builder;

@Builder
public class ServingsNumberFilter extends Filter {
    private int minServingsNumber;
    private int maxServingsNumber;

    public String getSql() {
        if (maxServingsNumber == 0) {
            return "servings_number >= " + minServingsNumber;
        }
        return "servings_number >= " + minServingsNumber + " AND servings_number <= " + maxServingsNumber;
    }
}
