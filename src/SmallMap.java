import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
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

        StringBuilder htmlStringBuilder = new StringBuilder();
        String htmlString;

        int pageCount = 1;
        while(true) {
            htmlString = Util.html(url + "/" + pageCount);
            if (htmlString.contains("<div class=\"board-list-item\">")) { // Check page validity
                htmlStringBuilder.append(htmlString);
                pageCount++;
            } else {
                break;
            }
        }

        String html = htmlStringBuilder.toString();

        Deque<String> href = new ArrayDeque<>();
        Deque<String> title = new ArrayDeque<>();

        Util.parse (html, "<span class=\"text\">(.*?)</span>", 1, title);
        Util.parse (html, "<div class=\"board-list-item\"><a href=\"(.*?)\" title=", 1, href);

        while(!title.isEmpty() && !href.isEmpty()) {
            smallMap.put(title.pollLast(), href.pollLast());
        }

        //ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Collection<Future> futures = new ArrayList<>();

        for(Map.Entry<String, String> entry : smallMap.entrySet()) {
            VideoUrl videoUrl = new VideoUrl(entry);
            futures.add(executorService.submit(videoUrl));
        }

        futures.forEach(future -> {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();

    }

}
