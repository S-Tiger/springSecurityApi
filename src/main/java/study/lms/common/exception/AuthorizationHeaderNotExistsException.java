package study.lms.common.exception;

public class AuthorizationHeaderNotExistsException extends RuntimeException {

    private static final long serialVersionUID = 4858506469476160448L;

    public AuthorizationHeaderNotExistsException() {
        super("토큰정보가 없습니다.");
    }
}
