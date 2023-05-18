package com.library.repository;

import com.library.dto.LikeDetailDto;
import com.library.entity.LikeBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeBookRepository extends JpaRepository<LikeBook,Long> {
    LikeBook findByLikeLikeIdAndBookBookId(Long likeId, Long bookId);

    @Query(" select new com.library.dto.LikeDetailDto(lb.likeBookId, i.bookName, bi.imageUrl)" +
            " from LikeBook lb, BookImage bi" +
            " join lb.book i " +
            " where lb.like.id = :likeId " +
            " and bi.book.id = lb.book.id " +
            " and bi.repImageYesNo = 'Y' " +
            " order by lb.regDate desc ")

    List<LikeDetailDto> findLikeDetailDtoList(Long likeId);
}
