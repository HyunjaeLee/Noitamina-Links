import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    private static final Logger logger = LoggerFactory.getLogger(MD5.class);

    public static String stringToMD5(String s) {

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

    public static String fileToMD5(String file) {
        String str = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            FileInputStream fis = new FileInputStream(file);
            byte[] dataBytes = new byte[1024];
            int nread = 0;
            while ((nread = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            }
            byte[] mdbytes = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            str = sb.toString();
         } catch (Exception e){
            logger.error(e.getMessage(), e);
         }
        return str;
    }

    public static String read(String file) {

        String str = "";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            str = br.readLine();
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return str;

    }

    public static void write(String file, String md5) {

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(md5);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

    }

}
