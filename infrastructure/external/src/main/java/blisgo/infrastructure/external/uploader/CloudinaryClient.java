package blisgo.infrastructure.external.uploader;

import blisgo.usecase.port.infra.FileUploadOutputPort;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.hexagonal.SecondaryAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
        var transformation = new Transformation()
                .quality("auto")
                .crop("scale")
                .fetchFormat("webp");

        String originalFilename = resource.getFilename();
        String extension = Objects.requireNonNull(originalFilename)
                .substring(originalFilename.lastIndexOf(".") + 1);
        String newFilename = UUID.randomUUID() + "." + extension;
        Path tmpPath = Paths.get(newFilename);

        var map = Map.ofEntries(
                Map.entry("folder", "board"),
                Map.entry("resource_type", "auto"),
                Map.entry("format", extension),
                Map.entry("transformation", transformation)
        );

        try {
            Files.copy(resource.getInputStream(), tmpPath, StandardCopyOption.REPLACE_EXISTING);
            File tmpFile = tmpPath.toFile();
            var result = cloudinary.uploader().upload(tmpFile, map);
            var option = "q_auto,f_webp/";
            var location = addOpt((String) Objects.requireNonNull(result).get("secure_url"), option);
            Files.deleteIfExists(tmpPath);
            return URI.create(location);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String addOpt(String str, String opt) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        int appendIndex = sb.indexOf("/v") + 1;
        sb.insert(appendIndex, opt);
        return sb.toString();
    }
}
