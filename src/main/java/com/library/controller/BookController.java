package com.library.controller;

import com.library.dto.BookDto;
import com.library.dto.BookListDto;
import com.library.dto.BookSearchDto;
import com.library.entity.Book;
import com.library.service.BookImageService;
import com.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BookController {
    // http://localhost:8080/admin/books/new

    //상품 등록 페이지로 이동
    @GetMapping(value = "/admin/books/new")
    public String bookForm(Model model){ //데이터를 넘겨주려면 model
        model.addAttribute("bookDto", new BookDto());
        return "/book/bookInsertForm";
    }

private final BookService bookService; // 컨트롤러가 진행되려면 서비스에 저장해야함
private final BookImageService bookImageService;

@Bean
public HiddenHttpMethodFilter hiddenHttpMethodFilter(){
    return new HiddenHttpMethodFilter();
}

    //상품등록이 되서 데이터로 저장되고 메인페이지로 움직임
    @PostMapping(value = "/admin/books/new")
    public String booknew(@Valid BookDto dto, BindingResult error, Model model,
                          @RequestParam("bookImageFile")List<MultipartFile> uploadedFile){ //데이터를 넘겨주려면 model
        System.out.println("여기로 오나요");
        if(error.hasErrors()){
            return "/book/bookInsertForm";
        }

        if(uploadedFile.get(0).isEmpty()){
            model.addAttribute("errorMessage","첫 번째 이미지는 필수 입력 값입니다.");
            return "/book/bookInsertForm";
        }

        try{
            bookService.saveBook(dto, uploadedFile);
            System.out.println("저장되었습니다");
        }catch (Exception err){
            err.printStackTrace();
            model.addAttribute("errorMessage","예외가 발생했습니다.");
            return "/book/bookInsertForm";
        }
        return "redirect:/";  //메인 페이지로 이동
    }

    @GetMapping(value = {"/admin/books","/admin/books/{page}"})
    //
    //
    // "/admin/products/{page}" 에서 page는 옵션이야 그래서 밑에 @PathVariable("page") Optional<Integer> page 에 integer을 Optional로 기재해 줌
    public String bookManage(BookSearchDto dto, @PathVariable("page") Optional<Integer> page, Model model){

        //사용자가 page를 가져와서 존재하면 존재하는 페이지 가져오고 없으면 첫번째 페이지 가져오기 size는 3개만 보여주세요.
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get(): 0,8);


        Page<Book> books = bookService.getAdminBookPage(dto,pageable);

        model.addAttribute("books", books);
        model.addAttribute("searchDto", dto);  // 검색 조건 유지를 위하여
        model.addAttribute("maxPage", 10);  // 하단에 보여줄 최대 페이지 번호

        return "/book/bookAdminList";
    }

    @GetMapping(value = {"/books/list","/books/list/{page}"})
    public String bookList(BookSearchDto dto, @PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0,8);

        if(dto.getSearchQuery() == null){
            dto.setSearchQuery("");
        }  // page 2쪽을 눌렀더니 널값으로 오류뜨고 처리가 안되서 null값이어도 널값으로 두고 처리해달라는 if 구문을 추가함

        Page<BookListDto> booklists = bookService.getListBookPage(dto,pageable);

        model.addAttribute("booklists", booklists);
        model.addAttribute("searchDto", dto);
        model.addAttribute("maxPage", 10);

        return "/book/bookList" ;
    }

    @GetMapping(value = "/admin/book/{bookId}")
    public String bookDetail(@PathVariable("bookId") Long bookId, Model model){
        try{
            BookDto dto = bookService.getBookDetail(bookId);
            model.addAttribute("bookDto", dto);
        } catch (EntityNotFoundException err){
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다.") ;
            model.addAttribute("bookDto", new BookDto()) ;
        }
        return "/book/bookUpdateForm";
    }

    @PostMapping(value = "/admin/book/{bookID}")
    public String bookUpdate(@Valid BookDto dto, BindingResult error, Model model,
                             @RequestParam("bookImageFile") List<MultipartFile> uploadedFile){

        String whenError = "/book/bookUpdateForm";

        if(error.hasErrors()){
            return whenError;
        }

        if(uploadedFile.get(0).isEmpty() && dto.getBookId()== null){
            model.addAttribute("errorMessage", "첫 번째 이미지는 필수 입력 사항입니다.");
            return whenError;
        }

        try{
            bookService.updateBook(dto,uploadedFile);

        }catch (Exception err){
            model.addAttribute("errorMessage", "상품 수정 중에 오류가 발생하였습니다.");
            err.printStackTrace();
            return whenError;
        }

        return "redirect:/admin/books";  //메인 페이지로 이동
    }


    //일반 사용자가 상품을 클릭하여 상세 페이지로 이동하기
    @GetMapping(value = "/book/{bookId}")
    public String productDetail(Model model, @PathVariable("bookId") Long bookId){
        BookDto dto = bookService.getBookDetail(bookId);
        model.addAttribute("dto", dto);

        return "/book/bookDetail";

    }


    //@PostMapping(value = "/admin/books/delete/{bookId}")
    //@DeleteMapping(value = "/admin/books/delete/{bookId}")
/*    @DeleteMapping("/admin/books/checkDelete/{bookId}")
    public ResponseEntity deleteBook(@PathVariable("bookId") List<Long> bookId) {


        System.out.println("테스트 : "+bookId);
        bookService.deleteBook(bookId);
        System.out.println("테스트 : 삭제실행확인");

        return new ResponseEntity<List<Long>>(bookId, HttpStatus.OK);
    }*/

    @DeleteMapping("/admin/books/checkDelete/{bookId}")
    public ResponseEntity deleteBook(@PathVariable("bookId") Long bookId) {

        bookService.deleteBook(bookId);
        return ResponseEntity.ok(bookId);
    }

    @GetMapping("/admin/books/delete/{bookId}")
    public String deleteByID(@PathVariable Long bookId){
        bookService.deleteBybookId(bookId);

        return "redirect:/admin/books";
    }



}


