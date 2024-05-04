package blisgo.usecase.port.infra;

import java.util.Map;

public interface WebScrapOutputPort {

    Map<String, Object> scrapPreview(String url);
}
