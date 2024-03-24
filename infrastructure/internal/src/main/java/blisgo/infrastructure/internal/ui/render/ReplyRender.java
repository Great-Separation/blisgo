package blisgo.infrastructure.internal.ui.render;

import blisgo.infrastructure.internal.persistence.community.mapper.ReplyMapper;
import blisgo.infrastructure.internal.ui.base.Router;
import blisgo.infrastructure.internal.ui.response.ReplyDTO;
import blisgo.usecase.request.reply.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Controller
@RequestMapping("/replies")
@RequiredArgsConstructor
public class ReplyRender extends Router {
    private final ReplyCommand commandUsecase;
    private final ReplyQuery queryUsecase;
    private final ReplyMapper mapper;


    @GetMapping("/{postId}")
    public ModelAndView replies(
            @PathVariable Long postId,
            @PageableDefault(sort = "createdDate", direction = ASC) Pageable pageable,
            @RequestParam(required = false, defaultValue = "0") Long lastReplyId
    ) {
        GetReply query = GetReply.builder()
                .postId(postId)
                .lastReplyId(lastReplyId)
                .pageable(pageable)
                .build();

        return new ModelAndView(
                routes(Router.Folder.COMMUNITY, Router.Page.CONTENT) + fragment(Router.Fragment.REPLIES),
                Map.of("replies", queryUsecase.getReplies(query)
                        .map(mapper::toDTO)
                        .map(ReplyDTO::withTimeDiff)
                )
        );
    }

    @PostMapping("/{postId}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView create(
            @PathVariable Long postId,
            @RequestParam String content
    ) {
        AddReply command = AddReply.builder()
                .postId(postId)
                .content(content)
                .build();

        commandUsecase.addReply(command);

        return new ModelAndView(
                routes(Router.Folder.COMMUNITY, Router.Page.CONTENT) + fragment(Router.Fragment.REPLIES)
        );
    }

    @DeleteMapping("/{replyId}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView delete(
            @PathVariable Long replyId
    ) {
        commandUsecase.removeReply(new RemoveReply(replyId));

        return new ModelAndView(
                routes(Router.Folder.COMMUNITY, Router.Page.CONTENT) + fragment(Router.Fragment.REPLIES)
        );
    }
}
