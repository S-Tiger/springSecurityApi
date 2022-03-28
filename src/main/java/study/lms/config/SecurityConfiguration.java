package study.lms.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import study.lms.common.security.AuthenticationFilter;
import study.lms.common.security.JwtProvider;
import study.lms.service.UserService;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // 어노테이션을 추가하여 Spring Security 설정할 클래스라고 정의
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final Environment env;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override // WebSecurity는 FiterChainProxy를 생성하는 필터
    public void configure(WebSecurity web)throws Exception{
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**"
                ,"/webjars/**", "/swagger-resources/**", "/swagger-ui.html");
    }

    @Override // HttpSecurity는 http요청에 대한 웹 기반 보안을 구성성
    protected void configure(HttpSecurity http)throws Exception{

        http.headers().frameOptions().disable(); // H2 Console Display 표시 때문에 선언
//       http.httpBasic().disable(); // REST API사용하여 기본설정 사용 안함 (기본설정은 비인증시 로그인폼 화면으로 리다이렉트)
        http.csrf().disable(); // REST API이므로 csrf 보안이 필요 없음
        // 페이지 권한 설정
        http.authorizeRequests()
                .antMatchers("/user/singup").permitAll()
                .antMatchers("/user/myinfo").hasRole("MEMBER")
                .antMatchers("/admin/**").hasRole("ADMIN");

        // 로그인 설정
        http.formLogin()
                .loginPage("/user/login") // login경로로 접근하면 SpringSecurity에서 제공하는 From사용, 컨트롤러에 URL 매핑이 되어야한다.
                .loginProcessingUrl("/user/loginProcess") // login 페이지에 login form의 프로세스 Url
                .successForwardUrl("/user/login/result") // 로그인 성공시 이동되는 페이지, 컨트롤러에서 URL 매핑이 되어야한다.
                .usernameParameter("userEmail") // 로그인 form에서 아이디는 name=username인 input을 기본으로 인식하는데, 해당 메서드를 통해 파라미터명을 변경할 수 있다.
                .permitAll();

        // 로그아웃 설정
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) // 로그아웃 기본 URL(/logout)이 아닌 다른 URL로 재정의
                .logoutSuccessUrl("/user/logout/result") // 로그 아웃시 이동되는 페이지, 컨트롤러에서 URL 매핑이 되어야한다.
                .invalidateHttpSession(true); // HTTP 세션을 초기화 하는 작업
        // 에러처리 핸들링
        http.exceptionHandling().accessDeniedPage("/error");

        http.addFilter(getAuthenticationFilter());
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }


    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager(), userService, jwtProvider);

        return authenticationFilter;
    }

}
