import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static String html(String url){

        StringBuilder stringBuilder = new StringBuilder();

        try {

            URLConnection urlConnection = new URL(url).openConnection();
            urlConnection.setConnectTimeout(30000);
            urlConnection.setReadTimeout(30000);
            urlConnection.addRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            String inputLine;
            while((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }

            bufferedReader.close();

        } catch (Exception e) {

            //e.printStackTrace();
            System.out.println(e.getMessage());
            return html(url); //re-try

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
