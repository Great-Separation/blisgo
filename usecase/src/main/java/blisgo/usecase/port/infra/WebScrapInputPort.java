package blisgo.usecase.port.infra;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebScrapInputPort {

    private final WebScrapOutputPort port;

    public Map<String, Object> scrapPreview(String url) {
        return port.scrapPreview(url);
    }
}
