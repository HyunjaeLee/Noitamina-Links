import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static final String NOITAMINA_JSON = "Noitamina.json";
    private static final String NOITAMINA_MD5 = "Noitamina.md5";
    private static final String BIGMAP_MD5 = "BigMap.md5";

    public static void main(String[] args) {

        // Deserialization

        Map<String, Map<String, String>> map = null;

        try {
            map = IO.readJson(NOITAMINA_JSON, TreeMap.class);
        } catch(Exception e) {
            logger.info(e.getMessage());
        }

        if(map == null) {
            map = new TreeMap<>();
        }

        // Thread Pool

        if(!MD5.read(NOITAMINA_MD5).equals(MD5.fileToMD5(NOITAMINA_JSON)) ||
                !MD5.read(BIGMAP_MD5).equals(BigMap.md5()) ||
                map.size() == 0) {
            Future.future(map);
        }

        //Download

        String file  = "/Users/Hyunjae/Downloads/건어물 여동생 우마루짱/";
        Path path = Paths.get(file);
        if(Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        map.get("건어물 여동생 우마루짱").forEach((k, v) -> {
            Download download = new Download(file + k + ".mp4", v);
            executorService.execute(download);
        });

        // Serialization

        try {
            IO.writeJson(NOITAMINA_JSON, map);
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }

        // Output

        IO.print(map);

        // MD5

        MD5.write(NOITAMINA_MD5, MD5.fileToMD5(NOITAMINA_JSON));
        MD5.write(BIGMAP_MD5, BigMap.md5());

    }

}
