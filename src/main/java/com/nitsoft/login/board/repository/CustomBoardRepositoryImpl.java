package com.nitsoft.login.board.repository;

import com.nitsoft.login.board.domain.dto.BoardThumbnailDto;
import com.nitsoft.login.board.domain.dto.QBoardThumbnailDto;
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
    public Page<BoardThumbnailDto> findByCondition(String keyword, Pageable Pageable) {
        List<BoardThumbnailDto> boards = queryFactory
                .select(new QBoardThumbnailDto(board.id, board.title, board.member.nickname))
                .from(board)
                .where(
                        titleOrContentContains(keyword)
                )
                .innerJoin(board.member, member)
                .fetchJoin()
                .limit(Pageable.getPageSize())
                .offset(Pageable.getOffset())
                .fetch();

        Long totalSize = queryFactory.select(board.count())
                .from(board)
                .where(titleOrContentContains(keyword))
                .fetchOne();

        return new PageImpl<>(boards, Pageable, totalSize);
    }

    private BooleanExpression titleOrContentContains(String keyword) {
        return keyword == null ?
                null :
                board.title.contains(keyword).or(board.content.contains(keyword));
    }
}
