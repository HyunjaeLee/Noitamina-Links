import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BigMap {

    public static Map<String, String> bigMap() {

        String url = "http://ani.today/";
        Document doc = null;

        try {
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla")
                    .timeout(10000)
                    .post();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element category = doc.select("div.category").first(); // div with class=category
        Elements elements = category.select("a[href~=list/\\d{2,}]");

        Map<String, String> bigMap = new HashMap<>();
        elements.forEach(element -> bigMap.put(element.text(), element.attr("abs:href")));

        return bigMap;

    }

}
