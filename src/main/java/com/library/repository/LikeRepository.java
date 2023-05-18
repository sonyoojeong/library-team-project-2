package com.library.repository;

import com.library.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {
    //로그인 한 회원의 관심목록을 구해줍니다.
    Like findByMemberMemberId(Long memberId);
}
