package blisgo.infrastructure.internal.persistence.community.repository;

import blisgo.infrastructure.internal.persistence.base.NoOffsetSliceHelper;
import blisgo.infrastructure.internal.persistence.community.model.JpaReply;
import blisgo.infrastructure.internal.persistence.community.model.QJpaReply;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReplyCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final JdbcTemplate jdbcTemplate;

    public Slice<JpaReply> find(Pageable pageable, Long postId, long lastReplyId) {
        var qJpaReply = QJpaReply.jpaReply;


        var results = jpaQueryFactory.selectFrom(qJpaReply)
                .where(qJpaReply.postId.eq(postId))
                .where(qJpaReply.replyId.gt(lastReplyId))
                .limit(pageable.getPageSize() + 1L)
                .fetch();

        boolean hasNext = NoOffsetSliceHelper.checkLastPage(results, pageable);

        return new SliceImpl<>(results, pageable, hasNext);
    }
}
