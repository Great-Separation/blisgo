package blisgo.usecase.port.infra;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadInputPort {

    private final FileUploadOutputPort port;

    public URI upload(Resource resource) {
        return port.uploadFile(resource);
    }
}
