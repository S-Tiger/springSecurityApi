package study.lms.domain.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserRequestDto {

    //@NotBlank : null과 빈 공백 문자열 (" ")을 허용하지 않음
    //@NotEmpty : null과 공백 문자열("")을 허용하지 않음
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email
    @ApiModelProperty(value = "이메일", required = true) // 스웨거 API 설명서 표시 어노테이션
    private String userEmail;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @ApiModelProperty(value = "회원명", required = true)
    private String userName;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{6,20}"/*(?=.*\W)*/,
            message = "비밀번호는 영문자, 숫자가 1개 이상 들어간 6~20자리의 비밀번호여야 합니다.")
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @ApiModelProperty(value = "비밀번호", required = true)
    private String userPwd;


    public UserEntity toEntity(){
        return UserEntity.builder()
                .userEmail(userEmail)
                .userName(userName)
                .userPwd(userPwd)
                .userRole(Role.MEMBER)
                .build();
    }
}
