package blisgo.infrastructure.external.storage;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import blisgo.infrastructure.external.fixture.FixtureMonkeySingleton;
import com.cloudinary.Api;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.api.ApiResponse;
import com.navercorp.fixturemonkey.FixtureMonkey;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;

@ExtendWith(MockitoExtension.class)
class CloudinaryClientTest {

    private final FixtureMonkey fixtureMonkey = FixtureMonkeySingleton.getInstance();

    @Mock
    private Resource resource;

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader;

    @Mock
    private Api api;

    @Mock
    private ApiResponse apiResponse;

    @InjectMocks
    private CloudinaryClient cloudinaryClient;

    @Nested
    @DisplayName("uploadFile 메서드는")
    class uploadFile {

        final String filename = "test.jpg";

        final InputStream inputStream = new ByteArrayInputStream(fixtureMonkey.giveMeOne(byte[].class));

        @Test
        @DisplayName("파일 업로드가 정상적으로 수행된다")
        void uploadFileSuccessfully() throws IOException {
            given(resource.getFilename()).willReturn(filename);
            given(resource.getInputStream()).willReturn(inputStream);
            given(cloudinary.uploader()).willReturn(uploader);
            given(uploader.upload(any(File.class), any(Map.class)))
                    .willReturn(Map.of("secure_url", "https://test.com"));

            var actual = cloudinaryClient.uploadFile(resource);

            then(resource).should(times(1)).getInputStream();
            assertNotNull(actual);
        }
    }

    @Nested
    @DisplayName("deleteResourcesWhereTagIsTemp 메서드는")
    class deleteResourcesWhereTagIsTemp {

        @Test
        @DisplayName("태그가 temp인 리소스가 정상적으로 삭제된다")
        void deleteResourcesWhereTagIsTempSuccessfully() throws Exception {
            given(cloudinary.api()).willReturn(api);
            given(api.deleteResourcesByTag(anyString(), any(Map.class))).willReturn(apiResponse);

            cloudinaryClient.deleteResourcesWhereTagIsTemp();

            then(api).should(times(1)).deleteResourcesByTag(anyString(), any(Map.class));
        }
    }

    @Nested
    @DisplayName("tagAs 메서드는")
    class tagAs {

        final List<String> filename = fixtureMonkey.giveMe(String.class, 100).stream()
                .filter(name -> !name.matches(".*[\\\\/:*?\"<>|.].*"))
                .map(name -> name.concat(".jpg"))
                .toList();

        final List<Path> paths = filename.stream().map(Paths::get).toList();

        final String tag = fixtureMonkey.giveMeOne(String.class);

        @Test
        @DisplayName("파일들이 정상적으로 태그 지정된다")
        void tagAsSuccessfully() throws IOException {
            given(cloudinary.uploader()).willReturn(uploader);
            given(uploader.replaceTag(anyString(), any(String[].class), any(Map.class)))
                    .willReturn(Map.of());

            cloudinaryClient.tagAs(paths, tag);

            then(uploader).should(times(1)).replaceTag(anyString(), any(String[].class), any(Map.class));
        }
    }
}
