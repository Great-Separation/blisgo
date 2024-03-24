package blisgo.infrastructure.internal.persistence.community.repository;

import blisgo.infrastructure.internal.persistence.base.NoOffsetSliceHelper;
import blisgo.infrastructure.internal.persistence.community.model.JpaPost;
import blisgo.infrastructure.internal.persistence.community.model.QJpaPost;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final JdbcTemplate jdbcTemplate;

    public Slice<JpaPost> find(Pageable pageable, long lastPostId) {
        var qJpaPost = QJpaPost.jpaPost;

        var fields = Projections.fields(JpaPost.class,
                qJpaPost.postId,
                qJpaPost.title,
                qJpaPost.content,
                qJpaPost.author,
                qJpaPost.views,
                qJpaPost.likes,
                qJpaPost.replies,
                qJpaPost.modifiedDate
        );

        var results = jpaQueryFactory.select(fields)
                .from(qJpaPost)
                .where(qJpaPost.postId.lt(lastPostId))
                .orderBy(qJpaPost.postId.desc())
                .limit(pageable.getPageSize() + 1L)
                .fetch();

        boolean hasNext = NoOffsetSliceHelper.checkLastPage(results, pageable);

        return new SliceImpl<>(results, pageable, hasNext);
    }

    public List<Long> findAllPostIds() {
        var qJpaPost = QJpaPost.jpaPost;

        return jpaQueryFactory.select(qJpaPost.postId)
                .from(qJpaPost)
                .fetch();
    }

    public boolean updateViewCount(Long postId, Long views) {
        var qJpaPost = QJpaPost.jpaPost;

        return jpaQueryFactory.update(qJpaPost)
                .set(qJpaPost.views, views)
                .where(qJpaPost.postId.eq(postId))
                .execute() > 0;
    }

    public boolean updateLike(Long postId, Boolean isLike) {
        var qJpaPost = QJpaPost.jpaPost;

        return jpaQueryFactory.update(qJpaPost)
                .set(qJpaPost.likes, qJpaPost.likes.add(Boolean.TRUE.equals(isLike) ? 1 : -1))
                .where(qJpaPost.postId.eq(postId))
                .execute() > 0;
    }
}

