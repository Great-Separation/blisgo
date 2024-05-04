package blisgo.infrastructure.external.ui;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import blisgo.infrastructure.external.fixture.FixtureMonkeySingleton;
import com.navercorp.fixturemonkey.FixtureMonkey;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IndexUIWallpaperChangerTest {

    private final FixtureMonkey fixtureMonkey = FixtureMonkeySingleton.getInstance();

    @Mock
    private UnsplashClient client;

    @InjectMocks
    private IndexUIWallpaperChanger changer;

    @Nested
    @DisplayName("changeWallpaperDaily 메서드는")
    class changeWallpaperDaily {

        final byte[] imageBytes = fixtureMonkey.giveMeOne(byte[].class);

        final String imageUrl = fixtureMonkey.giveMeOne(String.class);

        @Test
        @DisplayName("이미지를 성공적으로 변경한다")
        void imageChangedSuccessfully() throws IOException {
            // Given
            given(client.bringImageUrl()).willReturn(imageUrl);
            given(client.bringImage(anyString())).willReturn(imageBytes);

            // When
            changer.changeWallpaperDaily();

            // Then
            then(client).should(times(1)).bringImage(anyString());
        }

        @Test
        @DisplayName("이미지 가져오기 실패 시 예외를 던진다")
        void throwsExceptionWhenImageFetchFails() throws IOException {
            // Given
            given(client.bringImageUrl()).willReturn(null);
            given(client.bringImage(anyString())).willReturn(null);

            // When & Then
            assertThrows(RuntimeException.class, () -> changer.changeWallpaperDaily());
        }
    }
}
