package com.library.entity;

import com.library.repository.BookRepository;
import com.library.repository.RentBookRepository;
import com.library.repository.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class EntityMapping {

    @Autowired
    BookRepository bookRepository ;

    @Autowired
    RentRepository rentRepository ;

    @Autowired
    EntityManager em ;

    @Autowired
    RentBookRepository rentBookRepository ;
}
