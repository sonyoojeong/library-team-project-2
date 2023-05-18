package com.library.repository;

import com.library.entity.BookImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookImageRepository extends JpaRepository<BookImage,Long> {
    List<BookImage> findByBookBookIdOrderByBookImageIdAsc(Long bookId);

    BookImage findByBookBookIdAndRepImageYesNo(Long bookId, String repImageYesNo);



}
