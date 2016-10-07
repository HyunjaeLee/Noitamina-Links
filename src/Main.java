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

        if(!MD5.read("Noitamina.md5").equals(MD5.fileToMD5("Noitamina.json")) || !MD5.read("BigMap.md5").equals(BigMap.md5()) || map.size() == 0) {
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

        // MD5

        MD5.write("Noitamina.md5", MD5.fileToMD5("Noitamina.json"));
        MD5.write("BigMap.md5", BigMap.md5());

    }

}
