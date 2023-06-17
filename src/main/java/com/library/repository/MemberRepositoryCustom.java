package com.library.repository;

import com.library.dto.MyPageDto;

import java.util.List;

public interface MemberRepositoryCustom {

    List<MyPageDto> getMyPage(String username);

    
}
