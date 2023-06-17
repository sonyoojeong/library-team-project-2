package com.library.repository;

import com.library.dto.BookListDto;
import com.library.dto.BookSearchDto;
import com.library.dto.MainBookDto;
import com.library.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepositoryCustom {
    // 관리자만이 볼 수 있는 상품 페이지
    Page<Book> getAdminBookPage(BookSearchDto searchDto, Pageable pageable);

    Page<BookListDto> getListBookPage(BookSearchDto searchDto, Pageable pageable);

    Page<MainBookDto> getMainBookPage(Pageable pageable);
}

