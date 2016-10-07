import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

public class BigMap {

    private static final String URL = "http://ani.today/";

    public static Map<String, String> bigMap() {

        Document doc = IO.getConnection(URL, 5);
        Element category = doc.select("div.category").first(); // div with class=category
        Elements elements = category.select("a[href~=list/\\d{2,}]");

        Map<String, String> bigMap = new HashMap<>();
        elements.forEach(element -> bigMap.put(element.text(), element.attr("abs:href")));

        return bigMap;

    }

    public static String md5() {
        return MD5.stringToMD5(IO.getConnection(URL, 1).outerHtml());
    }

}
