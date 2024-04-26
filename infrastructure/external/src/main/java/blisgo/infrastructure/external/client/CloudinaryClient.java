package blisgo.infrastructure.external.client;

import blisgo.usecase.port.infra.FileUploadOutputPort;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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
                .quality("auto")
                .crop("scale")
                .fetchFormat("webp");

        String originalFilename = resource.getFilename();
        String extension = Objects.requireNonNull(originalFilename)
                .substring(originalFilename.lastIndexOf(".") + 1);
        String newFilename = UUID.randomUUID() + "." + extension;
        Path tmpPath = Paths.get(newFilename);

        var map = Map.ofEntries(
                Map.entry("folder", "temp"),
                Map.entry("resource_type", "auto"),
                Map.entry("format", extension),
                Map.entry("transformation", transformation)
        );

        try {
            Files.copy(resource.getInputStream(), tmpPath, StandardCopyOption.REPLACE_EXISTING);
            File tmpFile = tmpPath.toFile();
            var result = cloudinary.uploader().upload(tmpFile, map);

            var location = result.get("secure_url").toString();
            Files.deleteIfExists(tmpPath);
            return URI.create(location);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean truncateTemp() {
        try {
            var result = cloudinary.uploader().destroy("temp", Map.of("resource_type", "all"));
            return result.get("result").equals("ok");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean moveFile(Path path, String from, String to) {
        var imageFormats = List.of("jpg", "jpeg", "png", "gif", "webp", "avif");
        String filename = StringUtils.stripFilenameExtension(path.toString());
        String filenameExtension = StringUtils.getFilenameExtension(path.toString());

        boolean isImage = imageFormats.contains(filenameExtension);
        try {
            String fromPublicId = from + "/" + filename + (isImage ? "" : "." + filenameExtension);
            String toPublicId = to + "/" + filename + (isImage ? "" : "." + filenameExtension);

            cloudinary.uploader().rename(
                    fromPublicId,
                    toPublicId,
                    Map.of("resource_type", isImage ? "image" : "raw")
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}
