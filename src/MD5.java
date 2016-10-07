import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    private static final Logger logger = LoggerFactory.getLogger(MD5.class);

    public static String md5(String s) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
        }
        md.update(s.getBytes());
        byte byteData[] = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();

    }

    public static String read() {

        String str = "";
        try (BufferedReader br = new BufferedReader(new FileReader("MD5"))) {
            str = br.readLine();
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return str;

    }

    public static void write(String s) {

        try(BufferedWriter bw = new BufferedWriter(new FileWriter("MD5"))) {
            bw.write(s);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

    }

    public static boolean compare(String md5) {
        if(read().equals(md5)) {
            return true;
        } else {
            write(md5);
            return false;
        }
    }

}
