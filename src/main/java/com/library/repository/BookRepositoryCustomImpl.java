package com.library.repository;

import com.library.constant.BookRentalStatus;
import com.library.dto.BookListDto;
import com.library.dto.BookSearchDto;
import com.library.dto.QBookListDto;
import com.library.entity.Book;
import com.library.entity.QBook;
import com.library.entity.QBookImage;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class BookRepositoryCustomImpl implements BookRepositoryCustom{
    private JPAQueryFactory queryFactory;

    //Alt + Insert (생성자 생성하려고 단축키)


    public BookRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        //생성자를 생성해서 받고 객체를 quertFactory에 넣기 (? )
    }

    @Override   //관리자용 목록페이지
    public Page<Book> getAdminBookPage(BookSearchDto searchDto, Pageable pageable) {
        QueryResults<Book> results = this.queryFactory
                .selectFrom(QBook.book)
                .where(dateRange(searchDto.getSearchDateType()),  //전체기간 : dateRange 메소드
                        possibleStatusCondition(searchDto.getBookRentalStatus()),  // 화면 상태 : sellstatusCondition
                        searchByCondition(searchDto.getSearchBy(), searchDto.getSearchQuery()))  // 상품명, 검색어 조회 : searchByCondition
                .orderBy(QBook.book.bookId.asc())
                .offset(pageable.getOffset())     //몇번째부터 시작하겠다
                .limit(pageable.getPageSize())   //몇개를 보여주겠다 - pagesize
                .fetchResults();


        List<Book> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<BookListDto> getListBookPage(BookSearchDto searchDto, Pageable pageable) {
        QBook book = QBook.book;
        QBookImage bookImage = QBookImage.bookImage;
                
        
        QueryResults<BookListDto> results = this.queryFactory
                .select(
                        new QBookListDto(
                                book.bookId,
                                book.bookName,
                                book.author,
                                bookImage.imageUrl,
                                book.bookPublisher,
                                book.description

                        )
                )
                .from(bookImage)
                .join(bookImage.book, book)
                .where(bookImage.repImageYesNo.eq("Y"))   // bookimage의 대표 이미지가 Y인 (존재하는) 경우에만 리스트가 보여지게끔 한다
                .where(searchByCondition(searchDto.getSearchBy(),searchDto.getSearchQuery()))
                .orderBy(book.bookId.asc())
                .offset(pageable.getOffset())  //몇번째부터 시작하겠다
                .limit(pageable.getPageSize())  //몇개를 보여주겠다 - pagesize
                .fetchResults() ;


        List<BookListDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);  //페이지 인터페이스는 객체 생성 못한다
    }

    //메인 페이지
    @Override
    public Page<MainBookDto> getMainBookPage(Pageable pageable) {
        QBook book = QBook.book;
        QBookImage bookImage = QBookImage.bookImage;

        QueryResults<MainBookDto> results = this.jpaQueryFactory
                .select(
                        new QMainBookDto(
                        book.id,
                        book.bookName,
                        book.author,
                        book.publisher,
                        book.publishingDate.stringValue(),
                        book.description,
                        book.categoryId,
                        bookImage.imageUrl)
                )
                .from(bookImage)
                .join(bookImage.book, book)
                .where(bookImage.repImageYesNo.eq("Y"))
                .orderBy(book.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MainBookDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    //BooleanExpression 타입은 QueryDSL에서 사용하는 논리식을 나타내는 타입으로, 이를 활용하면 복잡한 쿼리를 간결하고 직관적으로 작성할 수 있습니다.

 /*  private BooleanExpression likeCondition(String searchQuery) {
        // 검색 키워드가 널이 아니면 like 연산을 수행합니다.

        return StringUtils.isEmpty(searchQuery) ? null : QBook.book.bookName.like("%" + searchQuery + "%") ;
    } */


    // 상품명, 검색어 조회 : searchByCondition
    private BooleanExpression searchByCondition(String searchBy, String searchQuery) {
        if(StringUtils.equals("bookName",searchBy)){ //상품 이름(명)으로 검색
            return QBook.book.bookName.like("%"+searchQuery + "%");

        }else if(StringUtils.equals("author",searchBy)){  //저자로 검색
            return QBook.book.author.like("%"+searchQuery + "%");
        }


        return null;
    }

    // 화면 상태(상태<대여가능,대여불가 등> : possibleStatusCondition
    private BooleanExpression possibleStatusCondition(BookRentalStatus bookRentalStatus) {
        return bookRentalStatus == null ? null : QBook.book.bookRentalStatus.eq(bookRentalStatus);
    }

    //전체기간 조회 : dateRange 메소드
    private BooleanExpression dateRange(String searchDateType) {
        // 특정 기간 내 조회 방식 : 1일, 1주, 1달
        LocalDateTime dateTime = LocalDateTime.now();
        if(StringUtils.equals("all",searchDateType) || searchDateType == null) { 
            return null;
        }else if(StringUtils.equals("1d",searchDateType)){
            dateTime = dateTime.minusDays(1);  // 현재시간에서 1일 뺌

        }else if(StringUtils.equals("1w",searchDateType)){
            dateTime = dateTime.minusWeeks(1);  // 현재시간에서 1주 뺌

        }else if(StringUtils.equals("1m",searchDateType)){
            dateTime = dateTime.minusMonths(1);  // 현재시간에서 1달 뺌

        }else if(StringUtils.equals("6m",searchDateType)){
            dateTime = dateTime.minusMonths(6);  // 현재시간에서 6달 뺌
        }
            
        return QBook.book.regDate.after(dateTime);  //regDate 등록일
    }


}
