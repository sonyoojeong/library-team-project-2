package com.library.entity;

import com.library.constant.BookRentalStatus;
import com.library.constant.RentStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rentals")
@Getter @Setter @ToString
public class Rent extends ServiceTime {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "rent_id")
    private Long rentId;    //대여 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;      //도서 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;      //회원 번호

    private int delayCount;   //연장 횟수

    @Enumerated(EnumType.STRING)
    private RentStatus rentStatus; // 대여 상태

    private LocalDateTime rentDate ;

    @OneToMany(mappedBy = "rent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RentBook> rentBooks
            = new ArrayList<>();


    public static Rent createRent(Member member, List<RentBook> rentBookList) {
        Rent rent = new Rent();
        rent.setMember(member);

        for (RentBook bean : rentBookList){
            rent.addRentBook(bean);
        }
        rent.setRentStatus(RentStatus.RENT);
        rent.setStartDate(LocalDateTime.now());
        return rent;
    }

    private void addRentBook(RentBook rentBook) {
        rentBooks.add(rentBook);
        rentBook.setRent(this);
    }


    // 대출 기간이 종료되었는지 확인하는 메서드
    public boolean isOverdue() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(getEndDate());
    }

}


