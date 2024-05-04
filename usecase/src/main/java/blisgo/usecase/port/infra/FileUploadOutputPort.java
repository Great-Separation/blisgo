package blisgo.usecase.port.infra;

import java.net.URI;
import org.springframework.core.io.Resource;

public interface FileUploadOutputPort {

    URI uploadFile(Resource resource);
}
