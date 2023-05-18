package com.library.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter @Setter
public abstract class SaveBy extends SaveTime{

    @CreatedBy      //엔티티 생성 시 사용자의 id를 기록
    private String createdBy;   //생성자

    @LastModifiedBy     //엔티티 수정 시 수정자의 id를 기록
    private String modifiedBy;  //수정자
}
