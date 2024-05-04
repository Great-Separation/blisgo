package blisgo.infrastructure.external.ui;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class IndexUIWallpaperChanger {

    private final UnsplashClient client;

    @Scheduled(zone = "UTC", cron = "0 0 0 * * *")
    public void changeWallpaperDaily() throws IOException {
        String imageUrl = client.bringImageUrl();
        byte[] imageBytes = client.bringImage(imageUrl);

        URI uri = bringWallpaperURI();
        Path wallpaperDir = Paths.get(uri);

        try (InputStream in = new ByteArrayInputStream(imageBytes);
                ReadableByteChannel rbc = Channels.newChannel(in);
                FileOutputStream fos = new FileOutputStream(wallpaperDir.toFile(), false)) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private URI bringWallpaperURI() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/assets/img/index_wallpaper.webp");
        return resource.getURI();
    }
}
