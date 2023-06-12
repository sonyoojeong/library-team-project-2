package com.library.mapper;

import com.library.dto.HopeBookSearchDto;
import com.library.entity.HopeBookAppForm;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper // 스프링이 이 객체를 자동으로 생성하고, sql 구문을 분석해서 처리해준다.
public interface HopeBookAppListMapperInterface {
    // 테이블 전체 조회
    @Select("SELECT\n" +
            "\t   @ROWNUM:=@ROWNUM+1\t\t\t\t\tAS no\n" +
            "\t, \tA.HOPE_BOOK_APP_SN      \t\t\tAS hopeBookAppSn-- 희망도서신청번호\n" +
            "    , A.MEMBER_ID\t\t\t\t\t\t\tAS memberId\t\t-- 멤버아이디  \n" +
            "    , A.BOOK_NAME\t\t\t    \t\t\tAS bookName\t\t-- 희망도서명\n" +
            "    , A.BOOK_PUBLISHER\t\t\t\t\tAS bookPublisher-- 출판사\n" +
            "    , A.AUTHOR\t\t\t\t\t\t\tAS author\t\t-- 저자\n" +
            "\t, DATE_FORMAT(A.PUBLISHING_DATE, '%Y-%m-%d')\t\t\t\t\tAS publishingDate-- 발행일\n" +
            "    , DATE_FORMAT(A.APLY_DT, '%Y-%m-%d')  AS aplyDt\t\t\t\t-- 신청일\n" +
            "\t, A.APLY_STTS\t\t\t\t\t\t\tAS aplyStts\t\t\t-- 신청상태(1:신청, 2:승인, 3:반려, 4:입고)\n" +
            "    , CASE WHEN A.APLY_STTS = '1' THEN '신청'\n" +
            "\t\t   WHEN A.APLY_STTS = '2' THEN '승인'\n" +
            "           WHEN A.APLY_STTS = '3' THEN '반려'\n" +
            "           WHEN A.APLY_STTS = '4' THEN '입고'\n" +
            "           ELSE '' END \t\t\t\t\tAS aplySttsNm  -- 신청상태명\n" +
            "    -- 신청상태(1:신청중, 2:입고대기중, 3:반려, 4:입고완료)\n" +
            "\t, A.APLY_REASON\t\t\t\t\t\tAS aplyReason-- 신청사유\n" +
            "\t, A.IMG_FILE\t\t\t\t\t\tAS image-- 이미지 파일\n" +
            "\t, A.IMG_LINK\t\t\t\t\t\tAS link-- 이미지 링크\n" +
            "\t, A.RVW_OPNN\t\t\t\t\t\tAS rvwOpnn-- 검토의견\n" +
            "FROM HOPE_BOOK_APP A \n" +
            "  , (SELECT @ROWNUM:=0) R\n" +
            " ORDER BY APLY_DT DESC " +
            ";")
    List<HopeBookAppForm> selectAll();

    // 테이블 검색조건으로 조회
    @Select("SELECT\n" +
            "\t   @ROWNUM:=@ROWNUM+1\t\t\t\t\tAS no\n" +
            "\t, \tA.HOPE_BOOK_APP_SN      \t\t\tAS hopeBookAppSn-- 희망도서신청번호\n" +
            "    , A.MEMBER_ID\t\t\t\t\t\t\tAS memberId\t\t-- 멤버아이디  \n" +
            "    , A.BOOK_NAME\t\t\t    \t\t\tAS bookName\t\t-- 희망도서명\n" +
            "    , A.BOOK_PUBLISHER\t\t\t\t\tAS bookPublisher-- 출판사\n" +
            "    , A.AUTHOR\t\t\t\t\t\t\tAS author\t\t-- 저자\n" +
            "\t, DATE_FORMAT(A.PUBLISHING_DATE, '%Y-%m-%d')\t\t\t\t\tAS publishingDate-- 발행일\n" +
            "    , DATE_FORMAT(A.APLY_DT, '%Y-%m-%d')  AS aplyDt\t\t\t\t-- 신청일\n" +
            "\t, A.APLY_STTS\t\t\t\t\t\t\tAS aplyStts\t\t\t-- 신청상태(1:신청중, 2:입고대기중, 3:반려, 4:입고완료)\n" +
            "    , CASE WHEN A.APLY_STTS = '1' THEN '신청중'\n" +
            "\t\t   WHEN A.APLY_STTS = '2' THEN '입고대기중'\n" +
            "           WHEN A.APLY_STTS = '3' THEN '반려'\n" +
            "           WHEN A.APLY_STTS = '4' THEN '입고완료'\n" +
            "           ELSE '' END \t\t\t\t\tAS aplySttsNm  -- 신청상태명\n" +
            "\t, A.APLY_REASON\t\t\t\t\t\tAS aplyReason-- 신청사유\n" +
            "FROM HOPE_BOOK_APP A \n" +
            "  , (SELECT @ROWNUM:=0) R\n" +
            "WHERE 1=1\n" +
            "AND BOOK_NAME LIKE CONCAT('%',(SELECT CASE WHEN #{dto.srchKind} = '1' THEN  #{dto.srchData}  \n" +
            "                                ELSE '' END\n" +
            "\t\t\t\t\t  FROM DUAL\n" +
            "\t\t\t\t\t),'%')\n" +
            "AND AUTHOR LIKE CONCAT('%',(SELECT CASE WHEN #{dto.srchKind} = '2' THEN #{dto.srchData}\n" +
            "                                ELSE '' END\n" +
            "\t\t\t\t\t  FROM DUAL\n" +
            "\t\t\t\t\t),'%')\n" +
            "                    \n" +
            "AND BOOK_PUBLISHER LIKE CONCAT('%',(SELECT CASE WHEN #{dto.srchKind} = '3' THEN #{dto.srchData}\n" +
            "                                ELSE '' END\n" +
            "\t\t\t\t\t  FROM DUAL\n" +
            "\t\t\t\t\t),'%')                    \n" +
            " ORDER BY APLY_DT DESC " +
            ";")
    List<HopeBookAppForm> selectBySrchData(@Param("dto") HopeBookSearchDto dto);

    // no 컬럼은 auto_increment 옵션이다.
    // ognl

    // 희망도서신청 등록하기
    @Insert("INSERT INTO HOPE_BOOK_APP(\n" +
            "\t  HOPE_BOOK_APP_SN\t-- int\n" +
            "\t, MEMBER_ID\t\t\t-- int\n" +
            "\t, EMAIL\t\t\t\t-- varchar(255)\n" +
            "      , PHONE\t\t\t\t-- int\n" +
            "\t, BOOK_NAME\t\t\t-- varchar(255)\n" +
            "\t, AUTHOR\t\t\t-- varchar(255)\n" +
            "\t, BOOK_PUBLISHER\t-- varchar(255)\n" +
            "\t, PUBLISHING_DATE\t-- datetime\n" +
            "\t, ISBN\t\t\t\t-- varchar(255)\n" +
            "\t, PRICE\t\t\t\t-- int\n" +
            "\t, APLY_REASON\t\t-- varchar(255)\n" +
            "\t, APLY_DT\t\t\t-- datetime\n" +
            "\t, APLY_STTS\t\t\t-- int\n" +
            "\t, IMG_LINK\t\t\t\n" +
            "\t, IMG_FILE\t\t\t\n" +
            "\n" +
            ")VALUES(\n" +
            "      (SELECT SN FROM (SELECT IFNULL(MAX(HOPE_BOOK_APP_SN), 0) + 1 AS SN FROM HOPE_BOOK_APP) AS AA)-- HOPE_BOOK_APP_SN\t-- int\n" +
            "\t, #{hopeBookAppForm.memberId} \t\t\t\t-- MEMBER_ID\t\t\t-- int\n" +
            "\t, #{hopeBookAppForm.email} \t\t  \t\t\t-- EMAIL\t\t\t\t-- varchar(255)\n" +
            "      , #{hopeBookAppForm.phone} \t   \t\t \t  \t-- PHONE\t\t\t\t-- int\n" +
            "\t, #{hopeBookAppForm.bookName} \t\t\t\t-- BOOK_NAME\t\t\t-- varchar(255)\n" +
            "\t, #{hopeBookAppForm.author} \t\t\t\t\t-- AUTHOR\t\t\t\t-- varchar(255)\n" +
            "\t, #{hopeBookAppForm.bookPublisher} \t\t\t\t-- BOOK_PUBLISHER\t\t      -- varchar(255)\n" +
            "\t, #{hopeBookAppForm.publishingDate} \t\t\t-- PUBLISHING_DATE\t\t-- datetime\n" +
            "\t, #{hopeBookAppForm.isbn} \t \t\t\t\t-- ISBN\t\t\t\t-- varchar(255)\n" +
            "\t, #{hopeBookAppForm.price} \t\t\t\t\t-- PRICE\t\t\t\t-- int\n" +
            "\t, #{hopeBookAppForm.aplyReason} \t\t\t\t-- APLY_REASON\t\t\t-- varchar(255)\n" +
            "\t, SYSDATE() \t\t\t\t-- APLY_DT\t\t\t\t-- datetime\n" +
            "\t, 1\t\t\t\t\t\t\t\t\t-- APLY_STTS\t\t\t-- int\n" +
            "\t, #{hopeBookAppForm.link}\t\t\t\t\t\t\t\t\t-- IMG_LINK\t\t\t-- varchar(250)\n" +
            "\t, #{hopeBookAppForm.image}\t\t\t\t\t\t\t\t\t-- IMG_FILE\t\t\t-- varchar(250)\n" +
            "\n" +
            ");")
    int Insert(@Param("hopeBookAppForm") final HopeBookAppForm hopeBookAppForm);

    // 도서목록 테이블에 있는 로우를 isbn으로 조회하여 카운트(없으면 : 0, 있으면 : 1) 가져오기
    @Select("Select count(*) from BOOKS_INFO where isbn = #{isbn};")
    int getIsbnCount(@Param("isbn") String isbn);


    @Update(
            "UPDATE HOPE_BOOK_APP SET APLY_STTS = 2 , RVW_OPNN = #{rvwOpnn} WHERE HOPE_BOOK_APP_SN = #{hopeBookAppSn};"
    )
    int updateApprovalAppForm(@Param("hopeBookAppSn") int hopeBookAppSn,@Param("rvwOpnn") String rvwOpnn);

    @Update(
            "UPDATE HOPE_BOOK_APP SET APLY_STTS = 3 , RVW_OPNN = #{rvwOpnn} WHERE HOPE_BOOK_APP_SN = #{hopeBookAppSn};"
    )
    int updateReturnAppForm(@Param("hopeBookAppSn") int hopeBookAppSn,@Param("rvwOpnn") String rvwOpnn);

    @Update(
            "UPDATE HOPE_BOOK_APP SET APLY_STTS = 4 , RVW_OPNN = #{rvwOpnn} WHERE HOPE_BOOK_APP_SN = #{hopeBookAppSn};"
    )
    int updateCompleteAppForm(@Param("hopeBookAppSn") int hopeBookAppSn,@Param("rvwOpnn") String rvwOpnn);
}



