import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SmallMap extends Thread {

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

        Set<VideoUrl> threadSet = new HashSet<>();
        for(Map.Entry<String, String> entry : smallMap.entrySet()) {
            VideoUrl videoUrl = new VideoUrl(entry);
            threadSet.add(videoUrl);
            videoUrl.start();
        }

        threadSet.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                //System.out.println(e.getMessage());
            }
        });

    }

}
