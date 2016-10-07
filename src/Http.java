import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Http {

    private static final Logger logger = LoggerFactory.getLogger(Http.class);

    private static final String USER_AGENT = "Mozilla";

    public static Document get(String url, int count) throws IOException {

        Document doc = null;

        for(int i = 0; i < count; i++) {

            try {
                doc = Jsoup.connect(url)
                        .userAgent(USER_AGENT)
                        //.timeout(timeInMillis)
                        .get();
            } catch (IOException e) {
                if(i < count) {
                    logger.info(e.getMessage());
                } else {
                    throw e;
                }
            }

        }

        return doc;

    }

}
