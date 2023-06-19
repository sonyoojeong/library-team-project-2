package com.library.repository;

import com.library.dto.MyPageDto;
import com.library.dto.QMyPageDto;
import com.library.entity.QMember;
import com.library.entity.QRent;
import com.library.entity.QRentBook;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{
    private JPAQueryFactory queryFactory;

    public MemberRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<MyPageDto> getMyPage(String username) {
        QMember member = QMember.member;
        QRentBook rentBook = QRentBook.rentBook;
        QRent rent = QRent.rent;



        List<MyPageDto> results = this.queryFactory
                .select(
                        new QMyPageDto(
                                member.memberId,
                                member.name,
                                rentBook.book.bookName,
                                rentBook.book.isbn,
                                rentBook.count,
                                rentBook.rent.rentStatus,
                                rentBook.rent.rentDate,
                                rentBook.rent.rentDate

                        )
                )
                .from(rentBook)
                .join(rentBook.rent, rent)
                .join(rent.member, member)
                .where(member.email.eq(username))
                .orderBy(rent.rentDate.desc())
                .fetch();

        return results;
    }


}
