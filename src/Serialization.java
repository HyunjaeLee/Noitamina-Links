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

public class Serialization {

    public static Object read(String file) throws IOException, ClassNotFoundException {

        Object object;
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        object = ois.readObject();
        ois.close();
        fis.close();
        return object;

    }

    public static void write(String file, Object object) throws IOException {

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

}
