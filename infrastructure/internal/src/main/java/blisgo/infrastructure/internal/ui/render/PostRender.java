package blisgo.infrastructure.internal.ui.render;

import blisgo.infrastructure.internal.persistence.community.mapper.PostMapper;
import blisgo.infrastructure.internal.ui.base.Router;
import blisgo.usecase.request.post.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostRender extends Router {
    private final PostCommand commandUsecase;
    private final PostQuery queryUsecase;
    private final PostMapper mapper;

    @GetMapping
    public ModelAndView posts(
            @PageableDefault(sort = "createdDate", direction = DESC) Pageable pageable,
            @RequestParam(required = false, defaultValue = "" + Long.MAX_VALUE) Long lastPostId,
            @RequestParam(required = false) Long postId
    ) {
        if (postId == null) {
            var query = GetPost.builder()
                    .pageable(pageable)
                    .postId(lastPostId)
                    .build();

            var posts = queryUsecase.getPosts(query);

            return new ModelAndView(
                    routes(Folder.COMMUNITY, Page.BOARD) + fragment(Fragment.POSTS),
                    Map.of("posts", posts.map(mapper::toDTO))
            );
        } else {
            var query = GetPost.builder()
                    .postId(postId)
                    .build();

            var post = queryUsecase.getPost(query);

            return new ModelAndView(
                    routes(Folder.COMMUNITY, Page.CONTENT) + fragment(Fragment.POST),
                    Map.ofEntries(
                            Map.entry("post", mapper.toDTO(post)),
                            Map.entry("readOnly", true)
                    )
            );
        }
    }

    @PostMapping
    @PatchMapping
    public RedirectView updateOrCreatePost(UpdatePost command) {
        if (command.postId() == null)
            commandUsecase.addPost(new AddPost(command.title(), command.content()));
        else {
            commandUsecase.updatePost(command);
        }

        return new RedirectView(routes(Folder.COMMUNITY), false);
    }

    @DeleteMapping("/{postId}")
    public RedirectView removePost(@PathVariable long postId) {
        commandUsecase.removePost(new RemovePost(postId));

        return new RedirectView(routes(Folder.COMMUNITY), false);
    }

    @PostMapping("/{postId}/like")
    @ResponseStatus(HttpStatus.OK)
    public void like(@PathVariable Long postId) {
        PostLike command = PostLike.builder()
                .postId(postId)
                .isLike(true)
                .build();

        commandUsecase.like(command);
    }

    @PostMapping("/{postId}/dislike")
    @ResponseStatus(HttpStatus.OK)
    public void dislike(@PathVariable Long postId) {
        PostLike command = PostLike.builder()
                .postId(postId)
                .isLike(false)
                .build();

        commandUsecase.like(command);
    }
}
