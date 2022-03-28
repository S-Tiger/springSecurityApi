package study.lms.common.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import study.lms.common.exception.AuthorizationHeaderNotExistsException;
import study.lms.common.exception.InvalidTokenException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationFilter implements HandlerInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("jwt");

        if (token == null){
            throw new AuthorizationHeaderNotExistsException();
        }
        if (!jwtProvider.isTokenValid(token)) {
            throw new InvalidTokenException();
        }
        request.setAttribute("userEmail", jwtProvider.getUserEmail(token));
        return true;
    }

}
