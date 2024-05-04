package blisgo.domain.community.event;

import blisgo.domain.community.vo.PostId;

public record PostUpdateEvent(PostId postId, String text) {}
