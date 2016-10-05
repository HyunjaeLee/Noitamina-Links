import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Map;

public class Output {

    public static void print(Map<String, Map<String, String>> map) {

        for (Map.Entry<String, Map<String, String>> bigEntry : map.entrySet()) {
            System.out.println(bigEntry.getKey());
            for (Map.Entry<String, String > smallEntry: bigEntry.getValue().entrySet()) {
                System.out.println(smallEntry.getKey() + " | " + smallEntry.getValue());
            }
        }

    }

    public static void text(String file, Map<String, Map<String, String>> map) {

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {

            for (Map.Entry<String, Map<String, String>> bigEntry : map.entrySet()) {

                bufferedWriter.write(bigEntry.getKey());
                bufferedWriter.newLine();

                for (Map.Entry<String, String > smallEntry: bigEntry.getValue().entrySet()) {

                    bufferedWriter.write(smallEntry.getKey() + " | " + smallEntry.getValue());
                    bufferedWriter.newLine();

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void html(String file, Map<String, Map<String, String>> map) {

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

            for (Map.Entry<String, Map<String, String>> bigEntry : map.entrySet()) {

                bufferedWriter.write("\t<h1>" + bigEntry.getKey() + "</h1>\n"); // Series

                for (Map.Entry<String, String > smallEntry: bigEntry.getValue().entrySet()) {
                    bufferedWriter.write("\t<p><a href=\"" + smallEntry.getValue() + "\">" + smallEntry.getKey() + "</a></p>\n"); // Episode
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
