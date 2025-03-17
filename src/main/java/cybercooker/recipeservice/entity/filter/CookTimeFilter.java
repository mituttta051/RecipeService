package cybercooker.recipeservice.entity.filter;

import lombok.Builder;

@Builder
public class CookTimeFilter extends Filter {
    private int minCookTime;
    private int maxCookTime;

    public String getSql() {
        if (maxCookTime == 0) {
            return "cook_time >= " + minCookTime;
        }
        return "cook_time >= " + minCookTime + " AND cook_time <= " + maxCookTime;
    }
}
