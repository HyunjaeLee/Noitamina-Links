import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {

        TreeMap<String, LinkedHashMap<String, String>> map = new TreeMap<>();

        List<SmallMap> threadList = new ArrayList<>();
        BigMap.bigMap().forEach((bigTitle, bigUrl) -> {
            LinkedHashMap<String, String> smallMap = new LinkedHashMap<>();
            map.put(bigTitle, smallMap);
            SmallMap smallMapThread = new SmallMap(bigUrl, smallMap);
            smallMapThread.setName(bigTitle);
            threadList.add(smallMapThread);
            smallMapThread.start();
        });

        /*
        int i = 0;
        Set<SmallMap> threadSet = new HashSet<>();
        for (Map.Entry<String, String> bigEntry : BigMap.bigMap().entrySet()) {
            LinkedHashMap<String, String> smallMap = new LinkedHashMap<>();
            map.put(bigEntry.getKey(), smallMap);
            SmallMap smallMapThread = new SmallMap(bigEntry.getValue(), smallMap);
            threadSet.add(smallMapThread);
            smallMapThread.start();
            i++;
            if(i == 5)
                break;
        }
        */

        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        String file = "Noitamina.html";

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {

            bufferedWriter.write(
                    "<!DOCTYPE HTML>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "\t<meta charset=\"UTF-8\">\n" +
                    "\t<title>Noitamina</title>\n" +
                    "</head>\n" +
                    "<body>\n"
            );

            for (Map.Entry<String, LinkedHashMap<String, String>> bigEntry : map.entrySet()) {

                System.out.println(bigEntry.getKey());

                bufferedWriter.write("\t<h1>" + bigEntry.getKey() + "</h1>\n");

                for (Map.Entry<String, String > smallEntry: bigEntry.getValue().entrySet()) {

                    System.out.println(smallEntry.getKey() + " | " + smallEntry.getValue());

                    bufferedWriter.write("\t<p><a href=\"" + smallEntry.getValue() + "\">" + smallEntry.getKey() + "</a></p>\n");

                }

            }

            bufferedWriter.write(
                    "</body>\n" +
                    "</html>\n"
            );

            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
