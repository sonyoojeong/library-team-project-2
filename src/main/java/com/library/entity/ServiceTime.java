package com.library.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter @Setter
public abstract class ServiceTime {

    @CreatedDate    //엔티티 생성 시 자동으로 시간을 기록
    @Column(updatable = false)
    private LocalDateTime startDate;

    @LastModifiedDate   //엔티티 수정 시 자동으로 시간을 갱신
    private LocalDateTime endDate;
}
