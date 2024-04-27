package blisgo.infrastructure.external.extract;

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
        validateUrl(url);
        Document doc = getDocument(url);

        String title = doc.title();
        String description = getMetaTagContent(doc, "description");
        String imageUrl = getMetaTagContent(doc, "og:image");

        return Map.ofEntries(
                Map.entry("success", 1),
                Map.entry("link", url),
                Map.entry("meta", Map.of(
                        "title", title,
                        "site_name", url,
                        "description", description,
                        "image", Map.of("url", imageUrl))
                )
        );
    }

    private void validateUrl(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            throw new RuntimeException("Invalid URL");
        }
    }

    private Document getDocument(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getMetaTagContent(Document doc, String tagName) {
        Element metaTag = doc.selectFirst("meta[name=" + tagName + "]");
        return metaTag != null ? metaTag.attr("content") : "";
    }
}
