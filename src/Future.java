import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Future {

    private static final Logger logger = LoggerFactory.getLogger(Future.class);

    public static void future(Map<String, Map<String, String>> map) {

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Collection<java.util.concurrent.Future> futures = new ArrayList<>();

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

        executorService.shutdown();

        futures.forEach(future -> {
            try {
                future.get();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });

    }

}
