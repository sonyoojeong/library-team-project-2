package com.library.dto;

import com.library.entity.BookImage;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class BookImageDto {

    private Long bookImageId;
    private String imageName;   //이미지 이름 (uuid)
    private String originalImageName;   //원본 이미지 이름
    private String imageUrl;    //이미지 경로
    private String repImageYesNo;   //대표 이미지 여부

    private static ModelMapper modelMapper = new ModelMapper(); //entity와 dto간의 getter, setter 반복을 도와주는 modelMapper

    public static BookImageDto of(BookImage bookImage){
        //입력되는 엔터티 정보를 이용하여 dto 객체에 매핑합니다
        return modelMapper.map(bookImage,BookImageDto.class);
    }


}
