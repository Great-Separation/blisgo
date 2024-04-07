package blisgo.infrastructure.internal.ui.rest;

import blisgo.usecase.port.infra.FileUploadInputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cloud")
public class CloudStorageUploader {
    private final FileUploadInputPort port;

    @PostMapping("/upload/file")
    public ResponseEntity<Map<String, Object>> upload(MultipartFile file) {
        Resource resource = file.getResource();

        URI fileEndpoint = port.upload(resource);

        var response = Map.ofEntries(
                Map.entry("success", 1),
                Map.entry("file", Map.of("url", fileEndpoint.toString()))
        );

        return ResponseEntity.ok(response);
    }
}
