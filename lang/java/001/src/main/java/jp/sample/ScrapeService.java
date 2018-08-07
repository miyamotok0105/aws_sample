package jp.sample;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;

public class ScrapeService {
    private String url = "https://qiita.com/";

    public String getTitle() throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements title = document.select("title");
        return title.text();
    }
}