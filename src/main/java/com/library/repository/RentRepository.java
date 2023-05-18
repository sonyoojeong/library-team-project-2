package com.library.repository;

import com.library.entity.Rent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RentRepository extends JpaRepository<Rent, Long> {
    @Query(" select r from Rent r" +
        " where r.member.email = :email" +
        " order by r.rentDate desc ")
    List<Rent> findRents(@Param("email") String email, Pageable pageable);

    @Query(" select count(r) from Rent r " +
        " where r.member.email = :email ")
    Long countRent(@Param("email")String email) ;
}
