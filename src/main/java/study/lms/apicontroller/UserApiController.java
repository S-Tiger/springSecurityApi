package study.lms.apicontroller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import study.lms.common.security.JwtProvider;
import study.lms.domain.user.UserRequestDto;
import study.lms.domain.user.UserResponseDto;
import study.lms.service.UserService;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserApiController {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    @ApiOperation(value = "회원가입", notes = "회원 정보를 저장합니다.")
    @PostMapping("/sign")
    public ResponseEntity<UserResponseDto> createUser(UserRequestDto userRequestDto){
        UserResponseDto result = userService.createUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @ApiOperation(value = "회원수정", notes = "회원 정보를 수정합니다.")
    @PutMapping
    public ResponseEntity<UserResponseDto> updateUser(UserRequestDto userRequestDto){
        UserResponseDto result = userService.updateUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "회원정보상세", notes = "회원 정보를 조회합니다.")
    @GetMapping("/{userEmail}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable("userEmail") String userEmail){
        UserResponseDto result = userService.getUserByEmail(userEmail);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "회원정보삭제", notes = "회원 정보를 삭제합니다.")
    @DeleteMapping("/{userEmail}")
    public ResponseEntity<UserResponseDto> deleteUser(@PathVariable("userEmail") String userEmail){
        UserResponseDto result = userService.getUserByEmail(userEmail);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "회원정보목록", notes = "회원 정보목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getUserList(@RequestParam("page") int page, @RequestParam("size") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<UserResponseDto> result = userService.getUserList(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "로그인", notes = "로그인 합니다.")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String userEmail, @RequestParam String password){
        UserResponseDto userResponseDto;
        try {
            userResponseDto = userService.getUserByEmail(userEmail);
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인정보가 맞지 않습니다.");
        }

        if (!passwordEncoder.matches(password, userResponseDto.getUserPwd())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인정보가 맞지 않습니다.");
        }
        String token = jwtProvider.createToken(userEmail);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
