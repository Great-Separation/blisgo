package blisgo.infrastructure.internal.ui.render;

import static org.springframework.data.domain.Sort.Direction.ASC;

import blisgo.infrastructure.internal.persistence.community.mapper.ReplyMapper;
import blisgo.infrastructure.internal.ui.base.Router;
import blisgo.usecase.request.reply.AddReply;
import blisgo.usecase.request.reply.GetReplies;
import blisgo.usecase.request.reply.RemoveReply;
import blisgo.usecase.request.reply.ReplyCommand;
import blisgo.usecase.request.reply.ReplyQuery;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/replies")
@RequiredArgsConstructor
public class ReplyRender extends Router {

    private final ReplyCommand commandUsecase;

    private final ReplyQuery queryUsecase;

    private final ReplyMapper mapper;

    @GetMapping
    public ModelAndView replies(
            @RequestParam Long postId,
            @RequestParam(required = false, defaultValue = "0") Long lastReplyId,
            @PageableDefault(sort = "createdDate", direction = ASC) Pageable pageable) {
        var query = GetReplies.builder()
                .postId(postId)
                .lastReplyId(lastReplyId)
                .pageable(pageable)
                .build();

        var replies = queryUsecase.getReplies(query);

        return new ModelAndView(
                routes(Router.Folder.COMMUNITY, Router.Page.CONTENT) + fragment(Router.Fragment.REPLIES),
                Map.ofEntries(Map.entry("replies", replies.map(mapper::toDTO)), Map.entry("postId", postId)));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ModelAndView create(AddReply command) {
        commandUsecase.addReply(command);

        return new ModelAndView(
                routes(Router.Folder.COMMUNITY, Router.Page.CONTENT) + fragment(Router.Fragment.REPLIES),
                Map.of("postId", command.postId()));
    }

    @DeleteMapping("/{replyId}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView delete(@PathVariable Long replyId) {
        commandUsecase.removeReply(new RemoveReply(replyId));

        return new ModelAndView(
                routes(Router.Folder.COMMUNITY, Router.Page.CONTENT) + fragment(Router.Fragment.REPLIES));
    }
}
