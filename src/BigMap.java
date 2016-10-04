import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.TreeMap;

public class BigMap {

    public static Map<String, String> bigMap() {

        String url = "http://ani.today";
        String html = Util.html(url);

        Map<String, String> bigMap = new TreeMap<>();

        Deque<String> urlDeque = new ArrayDeque<>();
        Deque<String> titleDeque = new ArrayDeque<>();
        Util.parse(
                html
                , "(https?://ani.today/list/)+\\d{2,}+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*"
                , 0
                , urlDeque );
        Util.parse(
                html
                , "<a href=\"https?://ani.today/list/+\\d{2,}+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*\">(.*?)</a>"
                , 1
                , titleDeque);

        while(!url.isEmpty() && !titleDeque.isEmpty()) {
            bigMap.put(titleDeque.poll(), urlDeque.poll());
        }

        return bigMap;

    }

}
