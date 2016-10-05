import java.util.Map;

public class VideoUrl implements Runnable {

    Map.Entry<String, String> entry;

    public VideoUrl(Map.Entry<String, String> entry) {
        this.entry = entry;
    }

    @Override
    public void run() {
        entry.setValue(Util.parse(Util.html(entry.getValue()), "<meta itemprop=\"contentURL\" content=\"(.*?)\">", 1));
    }
}
