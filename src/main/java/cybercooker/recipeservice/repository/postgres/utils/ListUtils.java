package cybercooker.recipeservice.repository.postgres.utils;

import java.sql.Array;
import java.util.List;

public class ListUtils {
    public static <T> List<T> sqlArrayToList(Array arr) {
        try {
            if (arr == null) {
                return List.of();
            }
            return List.of((T[]) arr.getArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }
}
