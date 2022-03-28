package study.lms.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicUpdate;
import study.lms.domain.BaseEntity;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@DynamicUpdate
@Table
@Entity(name = "USER")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY = AutoIncrement, Auto = MySql계열(AUTOINCREMENT), Oracle계열(SEQUENCE)
    private Long userNum;

    @Column(nullable = false, length = 50, unique = true)
    private String userEmail;

    @Column(nullable = false, length = 50)
    private String userName;

    @Column(nullable = false)
    private String userPwd;

    @Enumerated(EnumType.STRING) // Enum값을 어떤 형태로 저장할지 결졍 Default값은 int로 된 숫자
    @Column(nullable = false)
    private Role userRole;

    public String getUserRoleValue(){
        return this.userRole.getValue();
    }

    public UserEntity toUpdate(UserRequestDto userRequestDto){
        if(!StringUtils.isEmpty(userRequestDto.getUserEmail())) {
            this.userEmail = userRequestDto.getUserEmail();
        }
        if(!StringUtils.isEmpty(userRequestDto.getUserName())) {
            this.userName = userRequestDto.getUserName();
        }
        if(!StringUtils.isEmpty(userRequestDto.getUserPwd())) {

            this.userPwd = userRequestDto.getUserPwd();
        }
        return this;
    }
}