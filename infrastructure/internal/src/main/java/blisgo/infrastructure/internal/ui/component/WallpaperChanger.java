package blisgo.infrastructure.internal.ui.component;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Getter
@Slf4j
@Component
public class WallpaperChanger {
    private volatile String wallpaperUrl;

    @PostConstruct
    public void changeIndexWallpaperDaily() throws IOException, InterruptedException {
        wallpaperUrl = UnsplashClient.getImageUrl();
    }

}
