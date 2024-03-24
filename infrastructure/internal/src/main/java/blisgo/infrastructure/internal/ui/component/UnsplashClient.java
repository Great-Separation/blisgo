package blisgo.infrastructure.internal.ui.component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UnsplashClient {
    static final String HOST = "https://api.unsplash.com/photos/random";
    static final String QUERY = "waste,garbage,trash,recycling";
    static final String OPTIONS = "&fm=webp&w=1500&q=50&blur=50";
    static final String CLIENT_ID = "CTW7rq3n5wwaqHphLTlv47RsPHweBqy4QWe7_YVCvk8";

    public static void changeWallpaper() throws IOException, InterruptedException {
        Optional<String> imageUrl = Optional.of(getImageUrl());
        replaceImage(imageUrl.get());
    }


    public static String getImageUrl() throws IOException, InterruptedException {
        String link = String.format(HOST + "?query=" + QUERY + "&client_id=" + CLIENT_ID);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(link)).method("GET", HttpRequest.BodyPublishers.noBody()).build();

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonObj = mapper.readTree(response.body());
            jsonObj = jsonObj.get("urls");

            return jsonObj.get("raw").asText().concat(OPTIONS);
        }
    }

    private static void replaceImage(String editedImageLink) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(editedImageLink))
                .build();

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            ReadableByteChannel rbc = Channels.newChannel(response.body());
            Resource resource = new ClassPathResource("static/assets/img/index_wallpaper.webp");
            Path wallpaperDir = Paths.get(resource.getURI());
            log.info("파일 위치>" + wallpaperDir);

            try (FileOutputStream fos = new FileOutputStream(wallpaperDir.toFile())) {
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            }
        }
    }
}
