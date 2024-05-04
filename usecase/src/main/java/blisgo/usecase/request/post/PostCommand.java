package blisgo.usecase.request.post;

import org.jmolecules.architecture.hexagonal.PrimaryPort;

@PrimaryPort
public interface PostCommand {

    boolean addPost(AddPost command);

    boolean updatePost(UpdatePost command);

    boolean removePost(RemovePost command);

    boolean like(PostLike command);
}
