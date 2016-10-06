import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

public class IO {

    public static Object readObject(String file) throws IOException, ClassNotFoundException {

        Object object;
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        object = ois.readObject();
        ois.close();
        fis.close();
        return object;

    }

    public static void writeObject(String file, Object object) throws IOException {

        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
        oos.close();
        fos.close();

    }

    public static <T> T readJson(String file, Class<T> classOfT) throws IOException {

        T t;
        FileReader fr = new FileReader(file);
        t = new Gson().fromJson(fr, classOfT);
        fr.close();
        return t;

    }

    public static void writeJson(String file, Map map) throws IOException {

        String json = new Gson().toJson(map);
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(json);
        bw.close();

    }

    public static void print(Map<String, Map<String, String>> map) {

        for (Map.Entry<String, Map<String, String>> bigEntry : map.entrySet()) {
            System.out.println(bigEntry.getKey());
            for (Map.Entry<String, String > smallEntry: bigEntry.getValue().entrySet()) {
                System.out.println(smallEntry.getKey() + " | " + smallEntry.getValue());
            }
        }

    }

    public static void writeText(String file, Map<String, Map<String, String>> map) {

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

    public static void writeHtml(String file, Map<String, Map<String, String>> map) {

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
