package study.lms.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass // JPA Entity 클래스들이 BaseTimeEntity을 상속할 경우 필드들(createdDate, modifiedDate)도 칼럼으로 인식
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreatedDate // Entity가 생성되어 저장될 때 시간이 자동 저장
    private String createDate;

    @LastModifiedDate // Entity의 값을 변경할 때 시간이 자동 저장
    private String modifiedDate;


   @PrePersist // 데이터가 처음으로 생성된 시점
    public void onPrePersist() {
        this.createDate = LocalDateTime.now().toString();
//        Date 타입을 변경해야하는 경우
//        .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    @PreUpdate // 데이터가 변경된 시점
    public void onPreUpdate() {
        this.modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
//        Date 타입을 변경해야하는 경우
//        .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}
