package study.lms.domain.user;

import lombok.Data;

@Data
public class UserResponseDto {

    private String userEmail;

    private String userName;

    private String userPwd;

    private String userRole;

    private String createDate;

}
