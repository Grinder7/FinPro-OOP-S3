package json;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

// JSON lib
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONFile {
    final public static String path = "./src/json/data.json";

    public static Map<String, Object> toMap() {
        JSONObject jsonObj = new JSONObject(new HashMap<>());

        try {
            // Get json object from json file
            jsonObj = (JSONObject) new JSONParser()
                        .parse(new FileReader(path));
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        Map<String, Object> map = new HashMap<>();

        jsonObj.forEach((key, val) -> {
            map.put((String) key, val);
        });

        return map;
    }

    public static void write(Map<String, Object> newMap) {
        JSONObject jsonObj = new JSONObject(newMap);

        // Write json file
        try (FileWriter file = new FileWriter(path)) {
            file.write(jsonObj.toString());
            file.flush();
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
