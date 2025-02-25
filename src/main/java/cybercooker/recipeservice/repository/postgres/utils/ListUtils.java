package cybercooker.recipeservice.repository.postgres.utils;

import java.sql.Array;
import java.util.List;

public class ListUtils {
    public static List<Integer> intArrayToList(Array arr) {
        try {
            if (arr == null) {
                return List.of();
            }
            return List.of((Integer[]) arr.getArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }
}
