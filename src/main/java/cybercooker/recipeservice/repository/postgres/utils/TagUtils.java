package cybercooker.recipeservice.repository.postgres.utils;

import org.postgresql.util.PGobject;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TagUtils {
    public static Map<Integer, String> hstoreAsMap(String hstore) {
        Map<Integer, String> map = new HashMap<>();
        if (hstore != null && !hstore.isEmpty()) {
            hstore = hstore.substring(1, hstore.length() - 1);
            String[] entries = hstore.split("\", \"");
            for (String entry : entries) {
                String[] keyValue = entry.split("\"=>\"");
                map.put(Integer.parseInt(keyValue[0].trim()), keyValue[1].trim());
            }
        }
        return map;
    }

    public static PGobject mapAsHstore(Map<Integer, String> map) throws SQLException {
        PGobject hstore = new PGobject();
        hstore.setType("hstore");
        hstore.setValue(map.entrySet().stream()
                .map(entry -> entry.getKey() + "=>" + entry.getValue())
                .collect(Collectors.joining(",")));
        return hstore;
    }

    public static Map<Integer, String> map(List<String> values) {
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < values.size(); i++) {
            map.put(i, values.get(i));
        }
        return map;
    }

}
