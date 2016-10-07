import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SmallMap implements Runnable {

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
            try {
                doc = Http.get(url + "/" + pageCount, 5);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                e.printStackTrace();
            }
        });

    }

}
