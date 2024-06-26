package blisgo.infrastructure.internal.ui.rest;

import blisgo.usecase.port.infra.FileUploadInputPort;
import blisgo.usecase.port.infra.WebScrapInputPort;
import java.net.URI;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/editor")
public class WYSIWYGController {

    private final FileUploadInputPort fileUploadInputPort;

    private final WebScrapInputPort webScrapInputPort;

    @PostMapping("/upload/file")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Object>> upload(MultipartFile file) {
        Resource resource = file.getResource();
        String filename = Optional.ofNullable(file.getOriginalFilename()).orElse(file.getName());
        URI fileEndpoint = fileUploadInputPort.upload(resource);

        var response = Map.ofEntries(
                Map.entry("success", 1),
                Map.entry("file", Map.of("url", fileEndpoint.toString())),
                Map.entry("caption", filename));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/upload/url")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Object>> upload(String file) {
        var response = Map.ofEntries(
                Map.entry("success", 1), Map.entry("file", Map.of("url", file)), Map.entry("caption", file));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/link-preview")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Object>> linkPreview(@RequestParam String url) {
        var linkPreview = webScrapInputPort.scrapPreview(url);

        return ResponseEntity.ok(linkPreview);
    }
}
