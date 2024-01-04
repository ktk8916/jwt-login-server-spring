package com.nitsoft.login.board.repository;

import com.nitsoft.login.board.domain.entity.Board;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.nitsoft.login.board.domain.entity.QBoard.board;
import static com.nitsoft.login.member.domain.entity.QMember.member;

public class CustomBoardRepositoryImpl implements CustomBoardRepository {
    private final JPAQueryFactory queryFactory;
    public CustomBoardRepositoryImpl(EntityManager entityManager){
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<Board> findByCondition(String keyword, Pageable Pageable) {
        List<Board> boards = queryFactory
                .selectFrom(board)
                .innerJoin(board.member, member)
                .fetchJoin()
                .where(
                        titleContains(keyword),
                        board.deletedAt.isNull()
                )
                .limit(Pageable.getPageSize())
                .offset(Pageable.getOffset())
                .fetch();

        Long totalSize = queryFactory.select(board.count())
                .from(board)
                .where(titleContains(keyword))
                .fetchOne();

        return new PageImpl<>(boards, Pageable, totalSize);
    }

    private BooleanExpression titleContains(String keyword) {
        return keyword == null ? null : board.title.contains(keyword);
    }
}
