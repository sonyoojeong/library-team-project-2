package com.library.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Getter @Setter @ToString
public class Reserve extends ServiceTime {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long reservationId;     //예약 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;      //회원 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;      //도서 번호

    private LocalDateTime reservationDate;      //예약 일시
}
