import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.TreeMap;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        // Deserialization

        Map<String, Map<String, String>> map = null;

        String jsonFile = "Noitamina.json";

        try {
            map = IO.readJson(jsonFile, TreeMap.class);
        } catch(Exception e) {
            logger.info(e.getMessage());
        }

        if(map == null) {
            map = new TreeMap<>();
        }

        // Thread Pool

        if(map.size() == 0 || !MD5.compare(BigMap.md5())) {
            Future.future(map);
        }

        // Serialization

        try {
            IO.writeJson(jsonFile, map);
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }

        // Output

        IO.print(map);

    }

}
