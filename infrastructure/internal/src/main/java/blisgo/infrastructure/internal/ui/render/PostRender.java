package blisgo.infrastructure.internal.ui.render;

import blisgo.infrastructure.external.extract.JsonParser;
import blisgo.infrastructure.internal.persistence.community.mapper.PostMapper;
import blisgo.infrastructure.internal.ui.base.Router;
import blisgo.infrastructure.internal.ui.base.UIToast;
import blisgo.usecase.request.post.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private final UIToast toast;
    private final JsonParser jsonParser;

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
    public ModelAndView post(
            @PathVariable Long postId,
            @RequestParam(required = false, defaultValue = "false") boolean edit,
            Model model
    ) {
        if (postId != null) {
            var query = GetPost.builder()
                    .postId(postId)
                    .build();

            var post = queryUsecase.getPost(query);

            model.addAttribute("post", mapper.toDTO(post));
        }

        return new ModelAndView(
                routes(Folder.COMMUNITY, edit ? Page.WRITE : Page.CONTENT) + fragment(Fragment.POST)
        );
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public RedirectView addPost(AddPost command) {
        String content = command.content();
        String thumbnail = jsonParser.parseFirstImageUrl(content);
        String preview = jsonParser.parseFirstParagraph(content);

        command = command.toBuilder()
                .thumbnail(thumbnail)
                .preview(preview)
                .build();

        commandUsecase.addPost(command);

        return new RedirectView(routes(Folder.COMMUNITY), false);
    }

    @PatchMapping("/{postId}")
    @PreAuthorize("isAuthenticated()")
    public RedirectView updatePost(UpdatePost command) {
        String content = command.content();
        String thumbnail = jsonParser.parseFirstImageUrl(content);
        String preview = jsonParser.parseFirstParagraph(content);

        command = command.toBuilder()
                .thumbnail(thumbnail)
                .preview(preview)
                .build();

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
    @PreAuthorize("isAuthenticated()")
    public ModelAndView like(@PathVariable Long postId) {
        var command = PostLike.builder()
                .postId(postId)
                .isLike(true)
                .build();

        return new ModelAndView(
                routesToast(),
                commandUsecase.like(command) ?
                        toast.success("toast.post.like.success") :
                        toast.error("toast.post.like.error")

        );
    }

    @PostMapping("/{postId}/dislike")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView unlike(@PathVariable Long postId) {
        var command = PostLike.builder()
                .postId(postId)
                .isLike(false)
                .build();

        return new ModelAndView(
                routesToast(),
                commandUsecase.like(command) ?
                        toast.success("toast.post.unlike.success") :
                        toast.error("toast.post.unlike.error")
        );
    }
}
