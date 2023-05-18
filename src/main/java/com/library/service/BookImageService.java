package com.library.service;

import com.library.entity.BookImage;
import com.library.repository.BookImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class BookImageService {
    @Value("${bookImageLocation}")
    private String bookImageLocation; // 상품 이미지가 업로드 되는 경로


    private final BookImageRepository bookImageRepository;
    private final FileService fileService;

    //상품 이미지 정보 저장
    public void saveBookImage(BookImage bookImage, MultipartFile uploadedFile) throws Exception{

        String originalImageName = uploadedFile.getOriginalFilename();
        String imageName = "";
        String imageUrl = "";

        System.out.println("bookImageLocation : " + bookImageLocation);

        // 파일 업로드
        if(!StringUtils.isEmpty(originalImageName)){  //not null, (널이 아니면) 이름이 있으면 업로드 하자
            imageName = fileService.uploadFile(bookImageLocation,originalImageName,uploadedFile.getBytes());  //uuid + 확장자
            imageUrl = "/images/book/" + imageName;
            System.out.println("imageUrl : " + imageUrl);
        }


        bookImage.updateBookImage(originalImageName,imageName,imageUrl);
        bookImageRepository.save(bookImage);

    }

    public void updateBookImage(Long bookImageId, MultipartFile uploadFile) throws Exception{
        if(!uploadFile.isEmpty()){  //업로드 할 이미지가 있으면
            BookImage previousImage = bookImageRepository.findById(bookImageId).orElseThrow(EntityNotFoundException::new);

            if(!StringUtils.isEmpty(previousImage.getImageName())){
                fileService.deleteFile(bookImageLocation +"/" + previousImage.getImageName());
            }

            String originalImageName = uploadFile.getOriginalFilename();
            String imageName = fileService.uploadFile(bookImageLocation, originalImageName, uploadFile.getBytes());

            String imageUrl = "/images/book/" + imageName ;

            previousImage.updateBookImage(originalImageName, imageName, imageUrl);
        }


    }

}
