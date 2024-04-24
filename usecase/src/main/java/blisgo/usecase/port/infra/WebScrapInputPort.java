package blisgo.usecase.port.infra;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebScrapInputPort {
    private final WebScrapOutputPort port;

    public Map<String, Object> scrapPreview(String url) {
        return port.scrapPreview(url);
    }
}
