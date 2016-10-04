import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;

public class SmallMap {

    public static Map<String, String> smallMap(String url) {

        Map<String, String> smallMap = new LinkedHashMap<>();

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

        return smallMap;

    }

}
