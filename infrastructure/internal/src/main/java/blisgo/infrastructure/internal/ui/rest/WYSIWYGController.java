package blisgo.infrastructure.internal.ui.rest;

import blisgo.usecase.port.infra.FileUploadInputPort;
import blisgo.usecase.port.infra.WebScrapInputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/editor")
public class WYSIWYGController {
    private final FileUploadInputPort fileUploadInputPort;
    private final WebScrapInputPort webScrapInputPort;

    @PostMapping("/upload/file")
    public ResponseEntity<Map<String, Object>> upload(MultipartFile file) {
        Resource resource = file.getResource();
        String filename = Optional.ofNullable(file.getOriginalFilename()).orElse(file.getName());
        URI fileEndpoint = fileUploadInputPort.upload(resource);

        var response = Map.ofEntries(
                Map.entry("success", 1),
                Map.entry("file", Map.of("url", fileEndpoint.toString())),
                Map.entry("caption", filename)
        );

        return ResponseEntity.ok(response);
    }


    @GetMapping("/link-preview")
    public ResponseEntity<Map<String, Object>> linkPreview(@RequestParam String url) {
        var linkPreview = webScrapInputPort.scrapPreview(url);

        var response = Map.ofEntries(
                Map.entry("success", 1),
                Map.entry("link", url),
                Map.entry("meta", Map.of(
                        "title", linkPreview.get("title"),
                        "site_name", linkPreview.get("siteName"),
                        "description", linkPreview.get("description"),
                        "image", Map.of("url", linkPreview.get("imageUrl")))
                )
        );

        return ResponseEntity.ok(response);
    }
}
