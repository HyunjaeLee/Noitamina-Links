import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Download implements Runnable {

    private String file;
    private String url;

    public Download(String file, String url) {
        this.file = file;
        this.url = url;
    }

    @Override
    public void run() {

        try {
            URLConnection connection = new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla");
            ReadableByteChannel rbc = Channels.newChannel(connection.getInputStream());
            FileOutputStream fos = new FileOutputStream(new File(file));
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
