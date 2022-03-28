package study.lms.common.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import study.lms.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final UserService userService;
    private static final String TOKEN_SECERT = "test";
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60; // 1시간

    // 토큰생성
    public String createToken(String userEmail){
        return Jwts.builder()
                .setSubject(userEmail)    // 토큰암호화할 값
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRE_TIME)) // 유효시간 (현재 시간 + 설정값)
                .signWith(SignatureAlgorithm.HS256, TOKEN_SECERT) // 암호화방법 (알고리즘, 암호화값)
                .compact();

    }

    // 토큰으로 회원검증
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userService.loadUserByUsername(this.getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }

    // 토큰에서 회원이메일 정보 추출
    public String getUserEmail(String token) {
        return Jwts.parser().setSigningKey(TOKEN_SECERT).parseClaimsJws(token).getBody().getSubject();
    }

    // Request Header에서 Token값 추출
    public String getToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH_TOKEN");
    }

    // 토큰의 유효성 확인
    public boolean isTokenValid(String token) {
        boolean result = true;
        String subject = null;

        try {
            subject = Jwts.parser().setSigningKey(TOKEN_SECERT)
                    .parseClaimsJws(token).getBody()
                    .getSubject();
        } catch (Exception e) {
            result = false;
        }
        if (subject == null || subject.isEmpty()) {
            result = false;
        }
        return  result;
    }
}
