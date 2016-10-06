import org.jsoup.Jsoup;
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
            doc = Jsoup.connect(entry.getValue())
                    .userAgent("Mozilla")
                    .timeout(10000)
                    .post();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element element = doc.select("source[src]").first();
        entry.setValue(element.attr("abs:src"));

    }
}
