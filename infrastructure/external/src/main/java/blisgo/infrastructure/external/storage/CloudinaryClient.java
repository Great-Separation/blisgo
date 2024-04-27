package blisgo.infrastructure.external.storage;

import blisgo.usecase.port.infra.FileUploadOutputPort;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.api.ApiResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.architecture.hexagonal.SecondaryAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@SecondaryAdapter
@Component
@RequiredArgsConstructor
public class CloudinaryClient implements FileUploadOutputPort {
    private Cloudinary cloudinary;

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    private final List<String> imageFormats = List.of("jpg", "jpeg", "png", "gif", "webp", "avif");

    @PostConstruct
    private void init() {
        cloudinary = new Cloudinary(Map.ofEntries(
                Map.entry("cloud_name", cloudName),
                Map.entry("api_key", apiKey),
                Map.entry("api_secret", apiSecret)
        ));
    }

    @Override
    public URI uploadFile(Resource resource) {
        var transformation = new Transformation<>()
                .quality("auto:eco")
                .crop("scale")
                .fetchFormat("jpg")
                .flags("progressive:semi");

        String originalFilename = resource.getFilename();
        String filenameExtension = StringUtils.getFilenameExtension(originalFilename);
        Path path = Paths.get(UUID.randomUUID() + "." + filenameExtension);

        var map = Map.ofEntries(
                Map.entry("folder", "board"),
                Map.entry("resource_type", "auto"),
                Map.entry("transformation", transformation),
                Map.entry("tags", "temp")
        );

        try {
            Files.copy(resource.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            File tmpFile = path.toFile();
            var result = cloudinary.uploader().upload(tmpFile, map);

            var location = result.get("secure_url").toString();
            Files.deleteIfExists(path);
            return URI.create(location);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteResourcesWhereTagIsTemp() {
        try {
            LocalDate threeDaysAgo = LocalDate.now().minusDays(3);
            String tag = "temp:" + threeDaysAgo;

            boolean hasNext = true;
            while (hasNext) {
                ApiResponse response = cloudinary.api().deleteResourcesByTag(tag, Map.of());
                hasNext = response.get("partial") instanceof Boolean result && result;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void tagAs(List<Path> paths, String tag) {
        List<String> publicIds = getPublicIds(paths);

        try {
            cloudinary.uploader().replaceTag(
                    tag,
                    publicIds.toArray(String[]::new),
                    Map.of()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getPublicIds(List<Path> paths) {
        List<String> publicIds = new ArrayList<>();

        for (Path path : paths) {
            String filename = StringUtils.stripFilenameExtension(path.toString());
            String filenameExtension = StringUtils.getFilenameExtension(path.toString());
            boolean isImage = imageFormats.contains(filenameExtension);
            publicIds.add("board/" + filename + (isImage ? "" : "." + filenameExtension));
        }
        return publicIds;
    }
}
