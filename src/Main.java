import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {

        String file = "Noitamina.txt";

        TreeMap<String, LinkedHashMap<String, String>> map = new TreeMap<>();

        Set<SmallMap> threadSet = new HashSet<>();
        BigMap.bigMap().forEach((bigTitle, bigUrl) -> {
            LinkedHashMap<String, String> smallMap = new LinkedHashMap<>();
            map.put(bigTitle, smallMap);
            SmallMap smallMapThread = new SmallMap(bigUrl, smallMap);
            threadSet.add(smallMapThread);
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
            if(i == 50)
                break;
        }
        */

        threadSet.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                //System.out.println(e.getMessage());
            }
        });

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {

            for (Map.Entry<String, LinkedHashMap<String, String>> bigEntry : map.entrySet()) {

                System.out.println(bigEntry.getKey());

                bufferedWriter.write(bigEntry.getKey());
                bufferedWriter.newLine();

                for (Map.Entry<String, String > smallEntry: bigEntry.getValue().entrySet()) {

                    System.out.println(smallEntry.getKey() + " | " + smallEntry.getValue());

                    bufferedWriter.write(smallEntry.getKey() + " | " + smallEntry.getValue());
                    bufferedWriter.newLine();

                }

            }


            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println(e.getMessage());
        }


    }

}
