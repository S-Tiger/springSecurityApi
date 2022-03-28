package study.lms.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.lms.domain.user.UserRequestDto;
import study.lms.domain.user.UserResponseDto;
import study.lms.service.UserService;

/**
 * 회원 컨트롤러
 */
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    /**
     * 로그인 페이지
     * @return
     */
    @GetMapping(value = "/login")
    public String getLogin(){
        return "/user/login";
    }

    /**
     * 로그인 처리 페이지
     * @return
     */
    @PostMapping(value = "/login/result")
    public String loginResult(){
        return "/user/loginSuccess";
    }

    /**
     * 회원가입 페이지
     * @return
     */
    @GetMapping(value = "/signup")
    public String getSignUp(){
        return "/user/signup";
    }

    /**
     * 회원가입 처리
     * @param userRequestDto
     * @return
     */
    @PostMapping("/signup")
    public String postSignup(UserRequestDto userRequestDto) {
        try {
            userService.createUser(userRequestDto);
        }catch (IllegalStateException e){

        }
        return "redirect:/user/login";
    }

    /**
     * 로그아웃 결과 페이지
     * @return
     */
    @GetMapping("/logout/result")
    public String logout() {
        return "/user/logout";
    }

    /**
     * 내정보
     * @param authentication
     * @return
     */
    @GetMapping("/userInfo")
    public String userInfo(Authentication authentication, Model model) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        UserResponseDto result = userService.getUserByEmail(user.getUsername());
        model.addAttribute("loginUser", result);
        return "/user/userInfo";
    }


}