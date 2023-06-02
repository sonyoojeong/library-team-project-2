package com.library.service;

import com.library.dto.RentBookDto;
import com.library.dto.RentDto;
import com.library.dto.RentHistDto;
import com.library.entity.*;
import com.library.repository.BookImageRepository;
import com.library.repository.BookRepository;
import com.library.repository.MemberRepository;
import com.library.repository.RentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RentService {
    private final RentRepository rentRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private  final BookImageRepository bookImageRepository ;

    public Long rent(RentDto rentDto, String email) {
        Book book = bookRepository.findById(rentDto.getBookId())
                .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);
        List<RentBook> rentBookList = new ArrayList<>();
        RentBook rentBook = RentBook.createRentBook(book, rentDto.getCount());
        rentBookList.add(rentBook);
        Rent rent = Rent.createRent(member,rentBookList);
        rentRepository.save(rent);
        return  rent.getRentId();
    }


    public Page<RentHistDto> getRentList(String email, Pageable pageable){
        List<Rent> rents = rentRepository.findRents(email,pageable);
        Long totalCount = rentRepository.countRent(email);

        List<RentHistDto> rentHistDtos = new ArrayList<>() ;

        for(Rent rent : rents){
            RentHistDto rentHistDto = new RentHistDto(rent);
            List<RentBook> rentBooks = rent.getRentBooks();

            for(RentBook bean : rentBooks){
                BookImage bookImage = bookImageRepository.findByBookBookIdAndRepImageYesNo(bean.getBook().getBookId(),"Y");

                RentBookDto beanDto = new RentBookDto(bean, bookImage.getImageUrl());

                rentHistDto.addBookDto(beanDto);
            }
            rentHistDtos.add(rentHistDto);
        }
        return new PageImpl<RentHistDto>(rentHistDtos,pageable,totalCount) ;
    }
    //장바구니에서 주문할 상품 데이터를 전달 받아서 주문을 생성하는 로직을 구현합니다.
    public Long rents(List<RentDto> rentDtoList, String email){
        //orderDtoList : 상품 아이디와 수량을 가지고 있는 객체들의 모음

        Member member = memberRepository.findByEmail(email);

        List<RentBook> rentBookList = new ArrayList<>();  //주문할 상품 리스트

        for (RentDto dto : rentDtoList){
            Long bookId= dto.getBookId();
            Book book = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);

            int count = dto.getCount();
            RentBook rentBook = RentBook.createRentBook(book,count);

            rentBookList.add(rentBook);
        }
        Rent rent = Rent.createRent(member, rentBookList);
        rentRepository.save(rent);

        return rent.getRentId();
    }
}
