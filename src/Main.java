import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) {

        TreeMap<String, LinkedHashMap<String, String>> map = new TreeMap<>();


        /*
        int i = 0;
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Collection<Future> futures = new ArrayList<>();
        for (Map.Entry<String, String> bigEntry : BigMap.bigMap().entrySet()) {
            LinkedHashMap<String, String> smallMap = new LinkedHashMap<>();
            map.put(bigEntry.getKey(), smallMap);
            SmallMap smallMapThread = new SmallMap(bigEntry.getValue(), smallMap);
            futures.add(executorService.submit(smallMapThread));

            i++;
            if(i == 50) { break; }
        }
        */


        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Collection<Future> futures = new ArrayList<>();

        BigMap.bigMap().forEach((bigTitle, bigUrl) -> {
            LinkedHashMap<String, String> smallMap = new LinkedHashMap<>();
            map.put(bigTitle, smallMap);
            SmallMap smallMapThread = new SmallMap(bigUrl, smallMap);
            futures.add(executorService.submit(smallMapThread));
        });

        futures.forEach(future -> {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();

        //String file = "Noitamina.txt";
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

                 /* txt output
                    bufferedWriter.write(bigEntry.getKey());
                    bufferedWriter.newLine();
                    */

                for (Map.Entry<String, String > smallEntry: bigEntry.getValue().entrySet()) {

                    System.out.println(smallEntry.getKey() + " | " + smallEntry.getValue());

                    bufferedWriter.write("\t<p><a href=\"" + smallEntry.getValue() + "\">" + smallEntry.getKey() + "</a></p>\n");

                    /* txt output
                    bufferedWriter.write(smallEntry.getKey() + " | " + smallEntry.getValue());
                    bufferedWriter.newLine();
                    */

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
