package com.library.repository;

import com.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long>, QuerydslPredicateExecutor<Book>, BookRepositoryCustom {
    // 쿼리 메소드 작성
    // 상품의 이름으로 조회하여 목록을 반환받기
    List<Book> findBookByBookName(String bookName);



}
