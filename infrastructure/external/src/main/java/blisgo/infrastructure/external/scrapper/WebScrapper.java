package blisgo.infrastructure.external.scrapper;

import blisgo.usecase.port.infra.WebScrapOutputPort;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class WebScrapper implements WebScrapOutputPort {
    public Map<String, Object> scrapPreview(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            throw new RuntimeException("Invalid URL");
        }
        
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String title = doc.title();
        String siteName = url;
        String description = "";
        String imageUrl = "";

        Element metaDescription = doc.select("meta[name=description]").first();
        if (metaDescription != null) {
            description = metaDescription.attr("content");
        }

        Element metaImage = doc.select("meta[property=og:image]").first();
        if (metaImage != null) {
            imageUrl = metaImage.attr("content");
        }

        return Map.ofEntries(
                Map.entry("title", title),
                Map.entry("siteName", siteName),
                Map.entry("description", description),
                Map.entry("imageUrl", imageUrl)
        );
    }
}
