import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) {

        // Deserialization

        String serializationFile = "map.ser";

        Map<String, Map<String, String>> map;

        try {
            map = (Map<String, Map<String, String>>) Serialization.read(serializationFile);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            map = new TreeMap<>();
        }

        // Thread Pool

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Collection<Future> futures = new ArrayList<>();

        for (Map.Entry<String, String> bigEntry : BigMap.bigMap().entrySet()) {

            if (map.keySet().contains(bigEntry.getKey())) { // if 'map' contains "series"

                SmallMap smallMapThread = new SmallMap(bigEntry.getValue(), map.get(bigEntry.getKey()));
                futures.add(executorService.submit(smallMapThread));

            } else {

                Map<String, String> smallMap = new LinkedHashMap<>();
                map.put(bigEntry.getKey(), smallMap);
                SmallMap smallMapThread = new SmallMap(bigEntry.getValue(), smallMap);
                futures.add(executorService.submit(smallMapThread));

            }

        }

        futures.forEach(future -> {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();

        // Serialization

        try {
            Serialization.write(serializationFile, map);
        } catch(Exception e) {
            e.printStackTrace();
        }

        // Output

        Output.print(map);
        
        /*
        String htmlFile = "Noitamina.html";
        Output.html(htmlFile, map);
        String textFile = "Noitamina.txt";
        Output.text(textFile, map);
        */

    }

}
