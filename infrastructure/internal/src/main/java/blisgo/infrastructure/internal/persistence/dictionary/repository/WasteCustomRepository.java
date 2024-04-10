package blisgo.infrastructure.internal.persistence.dictionary.repository;

import blisgo.domain.dictionary.vo.Category;
import blisgo.infrastructure.internal.persistence.base.NoOffsetSliceHelper;
import blisgo.infrastructure.internal.persistence.dictionary.model.JpaWaste;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static blisgo.infrastructure.internal.persistence.dictionary.model.QJpaDogam.jpaDogam;
import static blisgo.infrastructure.internal.persistence.dictionary.model.QJpaWaste.jpaWaste;

@Repository
@RequiredArgsConstructor
public class WasteCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final JdbcTemplate jdbcTemplate;

    public Slice<JpaWaste> findWastesByMemberIdFromDogam(
            UUID memberId, Pageable pageable,
            LocalDateTime lastDogamCreatedDate
    ) {
        var fields = Projections.fields(
                JpaWaste.class,
                jpaWaste.wasteId,
                jpaWaste.name,
                jpaWaste.picture,
                jpaDogam.createdDate
        );

        var results = jpaQueryFactory.select(fields)
                .from(jpaWaste)
                .join(jpaDogam).on(jpaWaste.wasteId.eq(jpaDogam.dogamId.wasteId))
                .where(jpaDogam.dogamId.memberId.eq(memberId))
                .where(jpaDogam.createdDate.lt(lastDogamCreatedDate))
                .orderBy(jpaDogam.createdDate.desc())
                .limit(pageable.getPageSize() + 1L)
                .fetch();

        boolean hasNext = NoOffsetSliceHelper.checkLastPage(results, pageable);

        return new SliceImpl<>(results, pageable, hasNext);
    }

    public Slice<JpaWaste> findPartial(Pageable pageable, long lastWasteId) {
        var fields = Projections.fields(
                JpaWaste.class,
                jpaWaste.wasteId,
                jpaWaste.name,
                jpaWaste.picture
        );

        var results = jpaQueryFactory.select(fields)
                .from(jpaWaste)
                .where(jpaWaste.wasteId.gt(lastWasteId))
                .orderBy(jpaWaste.wasteId.asc())
                .limit(pageable.getPageSize() + 1L)
                .fetch();

        boolean hasNext = NoOffsetSliceHelper.checkLastPage(results, pageable);

        return new SliceImpl<>(results, pageable, hasNext);
    }

    public List<JpaWaste> findWastesByCategories(List<Category> categories) {
        var fields = Projections.fields(
                JpaWaste.class,
                jpaWaste.wasteId,
                jpaWaste.name,
                jpaWaste.picture
        );

        return jpaQueryFactory.select(fields)
                .from(jpaWaste)
                .where(jpaWaste.categories.any().in(categories))
                .orderBy(Expressions.numberTemplate(Integer.class, "function('rand')").asc())
                .limit(4)
                .fetch();
    }

    public boolean updatePopularity() {
        var sql = "UPDATE waste "
                + "JOIN (SELECT waste_id, NTILE(10) OVER (ORDER BY views) AS star FROM waste) AS w2 "
                + "SET popularity = w2.star "
                + "WHERE waste.waste_id = w2.waste_id";
        jdbcTemplate.update(sql);
        return true;
    }

    public List<Long> findWasteIds() {
        return jpaQueryFactory.select(jpaWaste.wasteId)
                .from(jpaWaste)
                .fetch();
    }

    public boolean updateViewCount(Long wasteId, Long increment) {
        return jpaQueryFactory.update(jpaWaste)
                .set(jpaWaste.views, jpaWaste.views.add(increment))
                .where(jpaWaste.wasteId.eq(wasteId))
                .execute() > 0;
    }
}
