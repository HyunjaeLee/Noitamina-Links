import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        String file = "Noitamina.txt";
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {

            for (Map.Entry<String, String> bigEntry : BigMap.bigMap().entrySet()) {

                System.out.println(bigEntry.getKey());

                bufferedWriter.write(bigEntry.getKey());
                bufferedWriter.newLine();

                for (Map.Entry<String, String> smallEntry : SmallMap.smallMap(bigEntry.getValue()).entrySet()) {

                    System.out.println(smallEntry.getKey() + " | " + Util.videoUrl(smallEntry.getValue()));

                    bufferedWriter.write(smallEntry.getKey() + " | " + Util.videoUrl(smallEntry.getValue()));
                    bufferedWriter.newLine();

                }

            }

            bufferedWriter.close();

        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

}
