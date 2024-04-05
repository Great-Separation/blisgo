package blisgo.infrastructure.internal.ui.render;

import blisgo.infrastructure.internal.persistence.community.mapper.PostMapper;
import blisgo.infrastructure.internal.ui.base.Router;
import blisgo.usecase.request.post.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
            @RequestParam(required = false, defaultValue = "" + Long.MAX_VALUE) Long lastPostId
    ) {
        var query = GetPost.builder()
                .pageable(pageable)
                .postId(lastPostId)
                .build();

        var posts = queryUsecase.getPosts(query);

        return new ModelAndView(
                routes(Folder.COMMUNITY, Page.BOARD) + fragment(Fragment.POSTS),
                Map.of("posts", posts.map(mapper::toDTO))
        );
    }

    @GetMapping("/{postId}")
    public ModelAndView post(@PathVariable Long postId) {
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

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public RedirectView addPost(AddPost command) {
        commandUsecase.addPost(command);

        return new RedirectView(routes(Folder.COMMUNITY), false);
    }

    @PatchMapping("/{postId}")
    @PreAuthorize("isAuthenticated()")
    public RedirectView updatePost(UpdatePost command) {
        commandUsecase.updatePost(command);

        return new RedirectView(routes(Folder.COMMUNITY, command.postId()), true);
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("isAuthenticated()")
    public RedirectView removePost(@PathVariable Long postId) {
        var command = new RemovePost(postId);

        commandUsecase.removePost(command);

        return new RedirectView(routes(Folder.COMMUNITY), false);
    }

    @PostMapping("/{postId}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    public void like(@PathVariable Long postId) {
        var command = PostLike.builder()
                .postId(postId)
                .isLike(true)
                .build();

        commandUsecase.like(command);
    }

    @PostMapping("/{postId}/dislike")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    public void dislike(@PathVariable Long postId) {
        var command = PostLike.builder()
                .postId(postId)
                .isLike(false)
                .build();

        commandUsecase.like(command);
    }
}
