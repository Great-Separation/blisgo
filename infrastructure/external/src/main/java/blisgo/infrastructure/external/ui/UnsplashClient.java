package blisgo.infrastructure.external.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class UnsplashClient {

    private static final RestClient restClient = RestClient.create();

    private final ObjectMapper objectMapper;

    @Value("${unsplash.host}")
    private String host;

    @Value("${unsplash.path}")
    private String path;

    @Value("${unsplash.query}")
    private String query;

    @Value("${unsplash.client-id}")
    private String clientId;

    @Value("${unsplash.options}")
    private String options;

    public byte[] bringImage(String imageUrl) {
        URI uri = UriComponentsBuilder.fromUriString(imageUrl)
                .queryParam("options", options)
                .build()
                .toUri();

        var response = restClient.get().uri(uri).retrieve();

        return response.body(byte[].class);
    }

    public String bringImageUrl() throws JsonProcessingException {
        URI uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(host)
                .path(path)
                .queryParam("query", query)
                .queryParam("client_id", clientId)
                .build()
                .toUri();

        var response = restClient.get().uri(uri).retrieve();

        JsonNode jsonObj = objectMapper.readTree(response.body(String.class));

        return jsonObj.get("urls").get("raw").asText().concat(options);
    }
}
