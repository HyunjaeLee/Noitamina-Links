import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Map;

public class VideoUrl implements Runnable {

    Map.Entry<String, String> entry;

    public VideoUrl(Map.Entry<String, String> entry) {
        this.entry = entry;
    }

    @Override
    public void run() {

        Document doc = null;
        try {
            doc = Http.get(entry.getValue(), 5);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element element = doc.select("source[src]").first();
        entry.setValue(element.attr("abs:src"));

    }
}
