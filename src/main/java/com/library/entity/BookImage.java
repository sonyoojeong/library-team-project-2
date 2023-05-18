package com.library.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "book_images")
@Getter @Setter @ToString
public class BookImage extends SaveBy{

    @Id
    @Column(name = "book_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookImageId;
    private String imageName;   //이미지 이름 (uuid)
    private String originalImageName;   //원본 이미지 이름
    private String imageUrl;    //이미지 경로
    private String repImageYesNo;   //대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;



    //이미지 정보를 업데이트 해주는 메소드 입니다.
    public void updateBookImage(String originalImageName,String imageName, String imageUrl){
        this.originalImageName = originalImageName;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }



}
