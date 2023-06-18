package com.library.service;

import com.library.dto.HopeBookSearchDto;
import com.library.entity.HopeBookAppForm;
import com.library.mapper.HopeBookAppListMapperInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HopeBookAppListService {

    private final HopeBookAppListMapperInterface hopeBookAppListMapperInterface;

    // 희망도서목록 전제조회
    public List<HopeBookAppForm> selectAll() { // in BoardMapperInterface
        return hopeBookAppListMapperInterface.selectAll();
    }

    // 희망도서목록 검색조건으로 조회
    public List<HopeBookAppForm> selectBySrchData(HopeBookSearchDto dto) { // in BoardMapperInterface

        return hopeBookAppListMapperInterface.selectBySrchData(dto);
    }

    // 승인
    public int approvalAppForm(int hopeBookAppSn, String rvwOpnn){
        return hopeBookAppListMapperInterface.updateApprovalAppForm(hopeBookAppSn, rvwOpnn);
    }

    // 반려
    public int returnAppForm(int hopeBookAppSn, String rvwOpnn){
        return hopeBookAppListMapperInterface.updateReturnAppForm(hopeBookAppSn, rvwOpnn);
    }

    // 입고완료
    public int completeAppForm(int hopeBookAppSn, String rvwOpnn){
        return hopeBookAppListMapperInterface.updateCompleteAppForm(hopeBookAppSn, rvwOpnn);
    }
    // 입고 완료 시 book_info 테이블에 인서트
    public int InsertBook(String bookName, String bookPublisher, String author, String publishingDate, String isbn){
        return hopeBookAppListMapperInterface.InsertBook(bookName, bookPublisher, author, publishingDate, isbn);
    }

   /*public static int Insert(HopeBookAppForm hopeBookAppForm){
        return HopeBookAppListMapperInterface.Insert(hopeBookAppForm);
    }*/

}
