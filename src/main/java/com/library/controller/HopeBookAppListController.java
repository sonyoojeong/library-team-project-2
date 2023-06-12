package com.library.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.dto.HopeBookSearchDto;
import com.library.entity.HopeBookAppForm;
import com.library.service.HopeBookAppListService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/library")
public class HopeBookAppListController {

    private final HopeBookAppListService hopeBookAppListService;

    // 최초 희망도서목록화면 오픈
    @GetMapping(value = "/hopeBookAppList")
    public String openHopeBookAppPage(Model model){
        // 조회
        List<HopeBookAppForm> hopeBookAppList = hopeBookAppListService.selectAll();
        System.out.println("리스트 개수:"+hopeBookAppList.size()+"개");
        // 모델에 조회한 리스트 담기
        model.addAttribute("list", hopeBookAppList);
        HopeBookSearchDto dto = new HopeBookSearchDto();
        model.addAttribute("searchDto", dto);
        // 페이지(희망도서신청목록.html)로 이동
        return "/bookApp/hopeBookAppList";
    }

    // 검색조건을 가지고 조회
    @GetMapping(value = "/hopeBookAppListBySrchData")
    public String hopeBookAppListBySrchData(HopeBookSearchDto dto, Model model) {

        System.out.println("1:::"+dto.getSrchData());
        System.out.println("2:::"+dto.getSrchKind());
        // 조회
        List<HopeBookAppForm> hopeBookAppList = hopeBookAppListService.selectBySrchData(dto);
        model.addAttribute("list", hopeBookAppList);
        model.addAttribute("searchDto", dto);
        return "/bookApp/hopeBookAppList";
    }

    // 승인
    @GetMapping(value = "/approvalAppList")
    public void approvalAppList(@RequestParam Map<String, Object> parameters, HopeBookSearchDto searchDto, Model model
    , HttpServletResponse resp) throws IOException, JSONException {


        /* playerList로 넘어온 데이터를 문자열로 변환 */
        String json = parameters.get("list").toString();
        ObjectMapper mapper = new ObjectMapper();

        //변환된 데이터를 List형태에 저장
        //JSON 파일을 Java 객체로 deserialization 하기 위해서 ObjectMapper의 readValue() 메서드를 이용
        List<Map<String, Object>> list = mapper.readValue(json, new TypeReference<ArrayList<Map<String, Object>>>(){});


        int updateCnt = 0;

        for(int i=0; i < list.size() ;i++){
            System.out.println("list(" + i + ") : " + list.get(i));
            // 순번
            int hopeBookAppSn = Integer.parseInt((String)list.get(i).get("sn"));
            // 검토의견
            String rvwOpnn = (String)list.get(i).get("rvwOpnn");

            System.out.println("list(" + i + "순번) : " + hopeBookAppSn);
            System.out.println("list(" + i + "검토의견) : " + rvwOpnn);

            // 승인상태로 update service(파라미터 순번)
            int resultCnt = hopeBookAppListService.approvalAppForm(hopeBookAppSn, rvwOpnn);
            updateCnt = updateCnt + resultCnt;
        }

        resp.setContentType("text/html;charset=utf-8");
        // 재조회

        JSONArray jsonArr = new JSONArray();
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("updateCnt",updateCnt);
        jsonArr.put(jsonObj);
        resp.setContentType("application/x-json; charst=utf-8");
        resp.getWriter().print(jsonArr);

    }

    // 반려
    @GetMapping(value = "/returnAppList")
    public void returnAppList(@RequestParam Map<String, Object> parameters, HopeBookSearchDto searchDto, Model model
            , HttpServletResponse resp) throws IOException, JSONException {


        /* playerList로 넘어온 데이터를 문자열로 변환 */
        String json = parameters.get("list").toString();
        ObjectMapper mapper = new ObjectMapper();

        //변환된 데이터를 List형태에 저장
        //JSON 파일을 Java 객체로 deserialization 하기 위해서 ObjectMapper의 readValue() 메서드를 이용
        List<Map<String, Object>> list = mapper.readValue(json, new TypeReference<ArrayList<Map<String, Object>>>(){});


        int updateCnt = 0;

        for(int i=0; i < list.size() ;i++){
            System.out.println("list(" + i + ") : " + list.get(i));
            // 순번
            int hopeBookAppSn = Integer.parseInt((String)list.get(i).get("sn"));
            // 검토의견
            String rvwOpnn = (String)list.get(i).get("rvwOpnn");

            System.out.println("list(" + i + "순번) : " + hopeBookAppSn);
            System.out.println("list(" + i + "검토의견) : " + rvwOpnn);

            // 반려상태로 update service(파라미터 순번)
            int resultCnt = hopeBookAppListService.returnAppForm(hopeBookAppSn, rvwOpnn);
            updateCnt = updateCnt + resultCnt;
        }

        resp.setContentType("text/html;charset=utf-8");
        // 재조회

        JSONArray jsonArr = new JSONArray();
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("updateCnt",updateCnt);
        jsonArr.put(jsonObj);
        resp.setContentType("application/x-json; charst=utf-8");
        resp.getWriter().print(jsonArr);

    }

    // 입고완료
    @GetMapping(value = "/completeAppList")
    public void completeAppList(@RequestParam Map<String, Object> parameters, HopeBookAppForm hopeBookAppForm, HopeBookSearchDto searchDto, Model model
            , HttpServletResponse resp) throws IOException, JSONException {


        /* playerList로 넘어온 데이터를 문자열로 변환 */
        String json = parameters.get("list").toString();
        ObjectMapper mapper = new ObjectMapper();

        //변환된 데이터를 List형태에 저장
        //JSON 파일을 Java 객체로 deserialization 하기 위해서 ObjectMapper의 readValue() 메서드를 이용
        List<Map<String, Object>> list = mapper.readValue(json, new TypeReference<ArrayList<Map<String, Object>>>(){});


        int updateCnt = 0;

        for(int i=0; i < list.size() ;i++){
            System.out.println("list(" + i + ") : " + list.get(i));
            // 순번
            int hopeBookAppSn = Integer.parseInt((String)list.get(i).get("sn"));
            // 검토의견
            String rvwOpnn = (String)list.get(i).get("rvwOpnn");

            System.out.println("list(" + i + "순번) : " + hopeBookAppSn);
            System.out.println("list(" + i + "검토의견) : " + rvwOpnn);

            // 입고완료 상태로 update service(파라미터 순번)
            int resultCnt = hopeBookAppListService.completeAppForm(hopeBookAppSn, rvwOpnn);
            updateCnt = updateCnt + resultCnt;

        }


        resp.setContentType("text/html;charset=utf-8");
        // 재조회

        JSONArray jsonArr = new JSONArray();
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("updateCnt",updateCnt);
        jsonArr.put(jsonObj);
        resp.setContentType("application/x-json; charst=utf-8");
        resp.getWriter().print(jsonArr);

    }
}


/*
    @GetMapping(value = {"/admin/products", "/admin/products/{page}"})
    public String productMange(ProductSearchDto dto, @PathVariable("page") Optional<Integer> page, Model model){
        // 제일 첫번째 페이지 3개만 보여줘
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<Product> products = productService.getAdminProductPage(dto, pageable);

        model.addAttribute("products", products);
        model.addAttribute("searchDto", dto); // 검색 조건 유지를 위하여
        model.addAttribute("maxPage", 5); // 하단에 보여줄 최대 페이지 번호

        return "product/prList";
    }*/
