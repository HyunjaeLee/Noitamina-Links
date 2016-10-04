import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        String file = "Noitamina.txt";
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {

            for(Map.Entry<String, String> bigEntry : bigMap().entrySet()) {

                System.out.println(bigEntry.getKey());

                bufferedWriter.write(bigEntry.getKey());
                bufferedWriter.newLine();

                for(Map.Entry<String, String> smallEntry : smallMap(bigEntry.getValue()).entrySet()) {

                    System.out.println(smallEntry.getKey() + " | " + videoUrl(smallEntry.getValue()));

                    bufferedWriter.write(smallEntry.getKey() + " | " + videoUrl(smallEntry.getValue()));
                    bufferedWriter.newLine();

                }

            }

            bufferedWriter.close();

        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

    public static Map<String, String> bigMap() {

        String url = "http://ani.today";
        String html = html(url);

        Map<String, String> bigMap = new TreeMap<>();

        Deque<String> urlDeque = new ArrayDeque<>();
        Deque<String> titleDeque = new ArrayDeque<>();
        parse(html, "(https?://ani.today/list/)+\\d{2,}+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*", 0, urlDeque );
        parse(html , "<a href=\"https?://ani.today/list/+\\d{2,}+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*\">(.*?)</a>", 1, titleDeque);

        while(!url.isEmpty() && !titleDeque.isEmpty()) {
            bigMap.put(titleDeque.poll(), urlDeque.poll());
        }

        return bigMap;

    }

    public static Map<String, String> smallMap(String url) {

        Map<String, String> smallMap = new LinkedHashMap<>();

        StringBuilder htmlStringBuilder = new StringBuilder();
        String htmlString;

        int pageCount = 1;
        while(true) {
            htmlString = html(url + "/" + pageCount);
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

        parse (html, "<span class=\"text\">(.*?)</span>", 1, title);
        parse (html, "<div class=\"board-list-item\"><a href=\"(.*?)\" title=", 1, href);

        while(!title.isEmpty() && !href.isEmpty()) {
            smallMap.put(title.pollLast(), href.pollLast());
        }

        return smallMap;

    }

    public static String videoUrl(String url) {

        return parse(html(url), "<meta itemprop=\"contentURL\" content=\"(.*?)\">", 1);

    }

    public static String html(String url){

        StringBuilder stringBuilder = new StringBuilder();

        try {

            URLConnection urlConnection = new URL(url).openConnection();
            urlConnection.addRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            String inputLine;
            while((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }

            bufferedReader.close();

        } catch (IOException e) {

            //e.printStackTrace();
            System.out.println(e.getMessage());

        }

        return stringBuilder.toString();

    }

    public static int parse(String data, String regex, int group, Collection<String> collection) { // Put all matches into the given collection and return the size of the collection

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(data);

        while(matcher.find()) {
            collection.add(matcher.group(group));
        }

        return collection.size();

    }

    public static String parse(String data, String regex, int group) { // Return the first match

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(data);

        if(matcher.find()) {
            return matcher.group(group);
        } else {
            return null;
        }

    }

}
