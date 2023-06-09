package com.library.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookMapperInterface {

    public void bookDelete(Long bookId)throws Exception;
}
