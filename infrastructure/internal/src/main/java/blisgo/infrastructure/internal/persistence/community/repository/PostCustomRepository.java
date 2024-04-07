package blisgo.infrastructure.internal.persistence.community.repository;

import blisgo.infrastructure.internal.persistence.base.NoOffsetSliceHelper;
import blisgo.infrastructure.internal.persistence.common.JpaAuthor;
import blisgo.infrastructure.internal.persistence.common.JpaContent;
import blisgo.infrastructure.internal.persistence.community.model.JpaPost;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static blisgo.infrastructure.internal.persistence.community.model.QJpaPost.jpaPost;
import static blisgo.infrastructure.internal.persistence.community.model.QJpaReply.jpaReply;
import static blisgo.infrastructure.internal.persistence.member.model.QJpaMember.jpaMember;

@Repository
@RequiredArgsConstructor
public class PostCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Slice<JpaPost> find(Pageable pageable, long lastPostId) {
        var memberField = Projections.fields(JpaAuthor.class,
                jpaMember.email,
                jpaMember.name,
                jpaMember.picture.as("picture")
        );

        var contentField = Projections.fields(JpaContent.class,
                jpaPost.content.thumbnail,
                jpaPost.content.preview
        );

        var fields = Projections.fields(JpaPost.class,
                jpaPost.postId,
                jpaPost.title,
                contentField.as("content"),
                memberField.as("author"),
                jpaPost.views,
                jpaPost.likes,
                jpaPost.modifiedDate,
                jpaReply.count().as("replies")
        );

        var results = jpaQueryFactory.select(fields)
                .from(jpaPost)
                .leftJoin(jpaMember).on(jpaPost.memberId.eq(jpaMember.memberId))
                .leftJoin(jpaReply).on(jpaPost.postId.eq(jpaReply.postId))
                .where(jpaPost.postId.lt(lastPostId))
                .groupBy(jpaPost.postId)
                .orderBy(jpaPost.postId.desc())
                .limit(pageable.getPageSize() + 1L)
                .fetch();

        boolean hasNext = NoOffsetSliceHelper.checkLastPage(results, pageable);

        return new SliceImpl<>(results, pageable, hasNext);
    }

    public List<Long> findAllPostIds() {
        var qJpaPost = jpaPost;

        return jpaQueryFactory.select(qJpaPost.postId)
                .from(qJpaPost)
                .fetch();
    }

    public boolean updateViewCount(Long postId, Long views) {
        var qJpaPost = jpaPost;

        return jpaQueryFactory.update(qJpaPost)
                .set(qJpaPost.views, views)
                .where(qJpaPost.postId.eq(postId))
                .execute() > 0;
    }

    public boolean updateLike(Long postId, Boolean isLike) {
        var qJpaPost = jpaPost;

        return jpaQueryFactory.update(qJpaPost)
                .set(qJpaPost.likes, qJpaPost.likes.add(Boolean.TRUE.equals(isLike) ? 1 : -1))
                .where(qJpaPost.postId.eq(postId))
                .execute() > 0;
    }

    public Optional<JpaPost> findByIdWithReplies(Long postId) {
        var memberField = Projections.fields(JpaAuthor.class,
                jpaMember.email,
                jpaMember.name,
                jpaMember.picture.as("picture")
        );

        var contentField = Projections.fields(JpaContent.class,
                jpaPost.content.text
        );

        var fields = Projections.fields(JpaPost.class,
                jpaPost.postId,
                jpaPost.title,
                contentField.as("content"),
                memberField.as("author"),
                jpaPost.views,
                jpaPost.likes,
                jpaPost.modifiedDate,
                jpaReply.count().as("replies")
        );

        var result = jpaQueryFactory.select(fields)
                .from(jpaPost)
                .leftJoin(jpaMember).on(jpaPost.memberId.eq(jpaMember.memberId))
                .leftJoin(jpaReply).on(jpaPost.postId.eq(jpaReply.postId))
                .where(jpaPost.postId.eq(postId))
                .groupBy(jpaPost.postId)
                .fetchOne();

        return Optional.ofNullable(result);
    }
}

