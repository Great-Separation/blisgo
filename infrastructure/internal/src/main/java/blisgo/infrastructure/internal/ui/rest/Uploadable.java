package blisgo.infrastructure.internal.ui.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public interface Uploadable {
    @PostMapping("/upload")
    String uploadAndReturnResourceUrl(final MultipartFile file);
}
