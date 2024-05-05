package blisgo.usecase.request.post;

import jakarta.validation.Valid;
import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.springframework.validation.annotation.Validated;

@Validated
@PrimaryPort
public interface PostCommand {

    boolean addPost(@Valid AddPost command);

    boolean updatePost(@Valid UpdatePost command);

    boolean removePost(@Valid RemovePost command);

    boolean like(@Valid PostLike command);
}
