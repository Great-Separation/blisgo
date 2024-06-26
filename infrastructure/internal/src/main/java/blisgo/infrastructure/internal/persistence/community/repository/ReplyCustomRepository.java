package blisgo.infrastructure.internal.persistence.community.repository;

import static blisgo.infrastructure.internal.persistence.community.model.QJpaReply.jpaReply;
import static blisgo.infrastructure.internal.persistence.member.model.QJpaMember.jpaMember;

import blisgo.infrastructure.internal.persistence.base.NoOffsetSliceUtil;
import blisgo.infrastructure.internal.persistence.common.JpaAuthor;
import blisgo.infrastructure.internal.persistence.community.model.JpaReply;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReplyCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Slice<JpaReply> find(Pageable pageable, Long postId, long lastReplyId) {
        var joinMember =
                Projections.fields(JpaAuthor.class, jpaMember.email, jpaMember.name, jpaMember.picture.as("picture"));

        var fields = Projections.fields(
                JpaReply.class,
                jpaReply.replyId,
                jpaReply.content,
                jpaReply.createdDate,
                jpaReply.modifiedDate,
                joinMember.as("author"));

        var results = jpaQueryFactory
                .select(fields)
                .from(jpaReply)
                .leftJoin(jpaMember)
                .on(jpaReply.memberId.eq(jpaMember.memberId))
                .where(jpaReply.postId.eq(postId))
                .where(jpaReply.replyId.gt(lastReplyId))
                .limit(pageable.getPageSize() + 1L)
                .fetch();

        boolean hasNext = NoOffsetSliceUtil.checkLastPage(results, pageable);

        return new SliceImpl<>(results, pageable, hasNext);
    }
}
