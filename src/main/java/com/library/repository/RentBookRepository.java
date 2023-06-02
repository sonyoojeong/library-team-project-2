package com.library.repository;

import com.library.entity.RentBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentBookRepository extends JpaRepository<RentBook, Long> {
}
