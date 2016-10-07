import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SmallMap implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SmallMap.class);

    private String url;
    private Map<String, String> smallMap;

    public SmallMap(String url, Map<String, String> smallMap) {
        this.url = url;
        this.smallMap = smallMap;
    }

    @Override
    public void run() {

        Document doc = null;
        Elements elements = new Elements();

        int pageCount = 1;
        while(true) {
            doc = IO.getConnection(url + "/" + pageCount, 5);
            Elements elementsTemp = doc.select("a[href*=view]");
            if(elementsTemp.size() == 0) {
                break;
            }
            elements.addAll(elementsTemp);
            pageCount++;
        }
        Collections.reverse(elements); // Ascending Sort

        elements.forEach(element -> {
            if(!smallMap.containsKey(element.attr("title"))){
                smallMap.put(element.attr("title"), element.attr("abs:href"));
            }
        });

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Collection<Future> futures = new ArrayList<>();

        smallMap.entrySet().forEach(entry -> {
            if (!entry.getValue().contains(".mp4")) {
                VideoUrl videoUrl = new VideoUrl(entry);
                futures.add(executorService.submit(videoUrl));
            }
        });

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
