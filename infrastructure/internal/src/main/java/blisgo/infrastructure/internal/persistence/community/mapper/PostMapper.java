package blisgo.infrastructure.internal.persistence.community.mapper;

import blisgo.domain.community.Post;
import blisgo.infrastructure.internal.persistence.base.PersistenceMapper;
import blisgo.infrastructure.internal.persistence.community.model.JpaPost;
import blisgo.infrastructure.internal.ui.response.PostDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper implements PersistenceMapper<Post, JpaPost, PostDTO> {
    private final ModelMapper mapper;

    @Override
    public JpaPost toEntity(Post domain) {
        return mapper.map(domain, JpaPost.class);
    }

    @Override
    public Post toDomain(JpaPost entity) {
        return mapper.map(entity, Post.class);
    }

    @Override
    public PostDTO toDTO(Post domain) {
        return mapper.map(domain, PostDTO.class);
    }
    
    public Post toDomain(PostDTO dto) {
        return mapper.map(dto, Post.class);
    }
}