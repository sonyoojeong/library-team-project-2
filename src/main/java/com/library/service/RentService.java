package com.library.service;

import com.library.dto.RentDto;
import com.library.entity.Book;
import com.library.entity.Member;
import com.library.entity.Rent;
import com.library.entity.RentBook;
import com.library.repository.BookRepository;
import com.library.repository.MemberRepository;
import com.library.repository.RentRepository;
import lombok.RequiredArgsConstructor;
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
}
