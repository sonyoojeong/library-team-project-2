package com.library.repository;

import com.library.entity.Rent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RentRepository extends JpaRepository<Rent,Long> {

    //email 멤버의 주문내역을 최신날짜것 부터 보여주세요

    @Query(" select r from Rent r" +
            " where r.member.email = :email" +
            " order by r.rentDate desc ")
    List<Rent> findRents(@Param("email") String email, Pageable pageable);


    //email의 멤버가 몇개를 주문했는지 개수를 구해주는 쿼리문
    @Query(" select count(r) from Rent r " +
            " where r.member.email = :email ")
    Long countRent(@Param("email")String email) ;
}
