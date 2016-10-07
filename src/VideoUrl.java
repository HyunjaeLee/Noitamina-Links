import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Map;

public class VideoUrl implements Runnable {

    Map.Entry<String, String> entry;

    public VideoUrl(Map.Entry<String, String> entry) {
        this.entry = entry;
    }

    @Override
    public void run() {

        Document doc = IO.getConnection(entry.getValue(), 5);
        Element element = doc.select("source[src]").first();
        entry.setValue(element.attr("abs:src"));

    }
}
