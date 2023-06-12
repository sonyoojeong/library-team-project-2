package com.library.service;

import com.library.entity.HopeBookAppForm;
import com.library.mapper.HopeBookAppListMapperInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HopeBookAppFormService {

    private final HopeBookAppListMapperInterface hopeBookAppListMapperInterface;

    public int getIsbnCount(String isbn) {
        return hopeBookAppListMapperInterface.getIsbnCount(isbn);
    }

    public int Insert(HopeBookAppForm hopeBookAppForm) {
        return hopeBookAppListMapperInterface.Insert(hopeBookAppForm);
    }

}
