package com.library.service;

import com.library.dto.BookDto;
import com.library.dto.BookImageDto;
import com.library.dto.BookListDto;
import com.library.dto.BookSearchDto;
import com.library.entity.Book;
import com.library.entity.BookImage;
import com.library.mapper.BookMapperInterface;
import com.library.repository.BookImageRepository;
import com.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookImageService bookImageService;
    private final BookMapperInterface bookMapperInterface;


    // 상품 등록
    public Long saveBook(BookDto dto, List<MultipartFile> uploadedFile) throws Exception {
        Book book = dto.createBook();
        bookRepository.save(book);

        // 상품에 들어가는 각 이미지들
        // 컬렉션에 들어가는거는 size로 표시
        for (int i = 0; i < uploadedFile.size(); i++) {
            BookImage bookImage = new BookImage();
            bookImage.setBook(book);

            if (i == 0) {
                bookImage.setRepImageYesNo("Y");
            } else {
                bookImage.setRepImageYesNo("N");
            }

            bookImageService.saveBookImage(bookImage, uploadedFile.get(i));
        }

        return book.getBookId();
    }

    private final BookImageRepository bookImageRepository;

    //등록된 상품 정보를 읽어들입니다.
    public BookDto getBookDetail(Long bookId) {

        // 주석은 내생각 정리
        List<BookImage> bookImageList = bookImageRepository.findByBookBookIdOrderByBookImageIdAsc(bookId);  // 엔터티로 일단 상품이미지를 불러옴

        List<BookImageDto> bookImageDtoList = new ArrayList<BookImageDto>(); //dto에다가 리스트를 만들어 놓아서 가져오게끔 만들어 놓고

        for (BookImage bookImg : bookImageList) {   // for 문으로 5번 돌려서 이미지를 mapper함수를 통해 케터세터로 불러오고 위에 만들어놓은 dto 이미지리스트에다가 저장함
            BookImageDto bookImgDto = BookImageDto.of(bookImg);
            bookImageDtoList.add(bookImgDto);
        }

        Book book = bookRepository.findById(bookId)    // 상품정보에 대해 bookId에 따라 정보를 불러옴
                .orElseThrow(EntityNotFoundException::new);

        BookDto dto = BookDto.of(book);    //mapper를 통해 게터 세터로 dto 이미지를 연결해줌

        dto.setBookImageDtoList(bookImageDtoList); // productdto에  맨위에서 만들어놓은 productimagedtolist 내용을 추가해서 상품수정목록을 만들어줌

        return dto;
    }

    //상품 수정, 화면에 입력한 것으로 수정하기 위해서 dto에서 데이터를 넘겨줘야 함
    public Long updateBook(BookDto bookDto, List<MultipartFile> uploadedFile) throws Exception {
        Book book = bookRepository.findById(bookDto.getBookId())
                .orElseThrow(EntityNotFoundException::new);

        //화면에서 넘어 온 데이터를 Entity 에게 전달합니다.
        book.updateBook(bookDto);

        //5개의 이미지들에 대한 아이디 목록
        List<Long> bookImageIds = bookDto.getBookImageIds();

        for (int i = 0; i < uploadedFile.size(); i++) {
            bookImageService.updateBookImage(bookImageIds.get(i), uploadedFile.get(i));

        }

        return book.getBookId();

    }

    public Page<Book> getAdminBookPage(BookSearchDto dto, Pageable pageable) {
        //상품 검색 조건 dto와 페이징 객체 pageable을 사용해서 페이징 객체를 구해줍니다.

        return bookRepository.getAdminBookPage(dto, pageable);
    }

    public Page<BookListDto> getListBookPage(BookSearchDto dto, Pageable pageable) {
        return bookRepository.getListBookPage(dto, pageable);

    }


/*    public void deleteBook(List<Long> bookIds) {
        System.out.println("서비스단 실행");
        for (Long bookId : bookIds) {
            Book book = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);
            List<BookImage> bookImages = bookImageRepository.findByBookBookIdOrderByBookImageIdAsc(bookId);
            System.out.println("for문 서비스단 실행");
            bookImageRepository.deleteAll(bookImages);
            bookRepository.delete(book);
        }
    }*/

    @Transactional
    public void deleteBook(Long bookId) {
        System.out.println("서비스단 실행");

            // Book 엔티티를 삭제합니다.
            bookRepository.deleteById(bookId);
        }


    public void deleteBybookId(Long bookId){
        bookRepository.deleteById(bookId);
    }

}
