package blisgo.infrastructure.internal.ui.render;

import static blisgo.infrastructure.internal.ui.base.ToastStatus.ERROR;
import static blisgo.infrastructure.internal.ui.base.ToastStatus.SUCCESS;
import static org.springframework.data.domain.Sort.Direction.DESC;

import blisgo.infrastructure.external.extract.JsonParser;
import blisgo.infrastructure.internal.persistence.community.mapper.PostMapper;
import blisgo.infrastructure.internal.ui.base.Router;
import blisgo.infrastructure.internal.ui.base.UIToast;
import blisgo.usecase.request.post.AddPost;
import blisgo.usecase.request.post.GetPost;
import blisgo.usecase.request.post.GetPosts;
import blisgo.usecase.request.post.PostCommand;
import blisgo.usecase.request.post.PostLike;
import blisgo.usecase.request.post.PostQuery;
import blisgo.usecase.request.post.RemovePost;
import blisgo.usecase.request.post.UpdatePost;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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
            @RequestParam(required = false, defaultValue = "" + Long.MAX_VALUE) Long lastPostId) {
        var query = GetPosts.builder().pageable(pageable).lastPostId(lastPostId).build();

        var posts = queryUsecase.getPosts(query);

        return new ModelAndView(
                routes(Folder.COMMUNITY, Page.BOARD) + fragment(Fragment.POSTS),
                Map.of("posts", posts.map(mapper::toDTO)));
    }

    @GetMapping("/{postId}")
    public ModelAndView post(
            @PathVariable long postId,
            @RequestParam(required = false, defaultValue = "false") boolean edit,
            @AuthenticationPrincipal DefaultOidcUser oidcUser,
            Model model) {
        if (postId > 0) {
            var query = GetPost.builder().postId(postId).build();

            var post = queryUsecase.getPost(query);

            if (oidcUser != null && !post.isAuthor(oidcUser.getEmail())) {
                edit = false;
            }

            model.addAttribute("post", mapper.toDTO(post));
        }

        return new ModelAndView(routes(Folder.COMMUNITY, edit ? Page.WRITE : Page.CONTENT) + fragment(Fragment.POST));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public RedirectView addPost(AddPost command) {
        String content = command.content();
        String thumbnail = jsonParser.parseFirstImageUrl(content);
        String preview = jsonParser.parseFirstParagraph(content);

        command = command.toBuilder().thumbnail(thumbnail).preview(preview).build();

        commandUsecase.addPost(command);

        return new RedirectView(routes(Folder.COMMUNITY), false);
    }

    @PatchMapping("/{postId}")
    @PreAuthorize("isAuthenticated()")
    public RedirectView updatePost(UpdatePost command) {
        String content = command.content();
        String thumbnail = jsonParser.parseFirstImageUrl(content);
        String preview = jsonParser.parseFirstParagraph(content);

        command = command.toBuilder().thumbnail(thumbnail).preview(preview).build();

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
        var command = PostLike.builder().postId(postId).isLike(true).build();

        return new ModelAndView(
                routesToast(),
                commandUsecase.like(command)
                        ? toast.popup(SUCCESS, "toast.post.like.success")
                        : toast.popup(ERROR, "toast.post.like.error"));
    }

    @DeleteMapping("/{postId}/like")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView dislike(@PathVariable Long postId) {
        var command = PostLike.builder().postId(postId).isLike(false).build();

        return new ModelAndView(
                routesToast(),
                commandUsecase.like(command)
                        ? toast.popup(SUCCESS, "toast.post.unlike.success")
                        : toast.popup(ERROR, "toast.post.unlike.error"));
    }
}
