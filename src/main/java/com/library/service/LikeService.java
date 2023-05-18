package com.library.service;

import com.library.dto.LikeBookDto;
import com.library.dto.LikeDetailDto;
import com.library.entity.Book;
import com.library.entity.Like;
import com.library.entity.LikeBook;
import com.library.entity.Member;
import com.library.repository.BookRepository;
import com.library.repository.LikeBookRepository;
import com.library.repository.LikeRepository;
import com.library.repository.MemberRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;
    private final LikeBookRepository likeBookRepository;

    // 장바구니에 상품을 담는 로직을 구현합니다
    //likeBookDto 객체 정보와 사용자의 email을 이용하여 카트에 상품을 추가합니다.
    //책 id, 이메일 정보를 이용하여 관심목록(likeBook) Entity를 생성해 줍니다.
    //신규 '카트 상품'이면, 내 카트에 추가하고, 아니면 기존 수량에 값을 누적시켜 줍니다.

    public Long addLike(LikeBookDto likeBookDto, String email) throws NotFoundException {

        Member member = memberRepository.findByEmail(email);

        Long memberId = member.getMemberId();

        Like like = likeRepository.findByMemberMemberId(memberId);

        if(like == null){  // 관심목록이 없으면 관심목록 생성하기
            like = Like.CreateLike(member);
            likeRepository.save(like);
        }

        Long bookId = likeBookDto.getBookId();

        Book book = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);

        Long likeId = like.getLikeId();
        LikeBook savedLikeBook = likeBookRepository.findByLikeLikeIdAndBookBookId(likeId,bookId);

        if(savedLikeBook != null){
            throw new NotFoundException("이미 관심목록에 추가 되어있습니다.");
        }else{
            savedLikeBook = LikeBook.createLikeBook(like,book);
        }
        likeBookRepository.save(savedLikeBook);
        return savedLikeBook.getLikeBookId();
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @Transactional(readOnly = true) //읽기전용 속성(조회용)
    public List<LikeDetailDto> getLikeList(String email) {

      Member member = memberRepository.findByEmail(email);

      Long memberId = member.getMemberId();

      Like like = likeRepository.findByMemberMemberId(memberId);

      List<LikeDetailDto> likeDetailDtoList = new ArrayList<>();

      if (like == null) {
          return likeDetailDtoList; // return empty list
      } else {
          likeDetailDtoList = likeBookRepository.findLikeDetailDtoList(like.getLikeId());
          return likeDetailDtoList;

      }
    }

    public void deleteLikeBook(Long likeBookId){
        LikeBook likeBook = likeBookRepository.findById(likeBookId).orElseThrow(EntityNotFoundException::new);

        likeBookRepository.delete(likeBook);

    }

    @Transactional(readOnly = true)  // 이 회원이 수정/삭제 권한이 있는 지 체크합니다.
    public boolean validateLikeBook(Long likeBookId, String email) {
        Member current = memberRepository.findByEmail(email);  // 로그인 한 사람

        LikeBook likeBook = likeBookRepository.findById(likeBookId).orElseThrow(EntityNotFoundException::new);


        Member saved = likeBook.getLike().getMember();  // 카트 소유자

        if(StringUtils.equals(current.getEmail(), saved.getEmail())){
            return true;
        }else {
            return false;
        }

    }


  }




