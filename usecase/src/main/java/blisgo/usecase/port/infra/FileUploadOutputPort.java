package blisgo.usecase.port.infra;

import org.springframework.core.io.Resource;

import java.net.URI;

public interface FileUploadOutputPort {
    URI uploadFile(Resource resource);
}