package blisgo.infrastructure.external.ui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@Slf4j
@Component
@RequiredArgsConstructor
public class IndexUIWallpaperChanger {
    private final UnsplashClient client;

    @Scheduled(zone = "UTC", cron = "0 0 0 * * *")
    public void changeWallpaperDaily() throws IOException {
        byte[] imageBytes = client.bringImage();

        ClassPathResource resource = new ClassPathResource("static/assets/img/index_wallpaper.webp");
        Path wallpaperDir = Paths.get(resource.getURI());

        try (
                InputStream in = new ByteArrayInputStream(imageBytes);
                ReadableByteChannel rbc = Channels.newChannel(in);
                FileOutputStream fos = new FileOutputStream(wallpaperDir.toFile(), false)
        ) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
