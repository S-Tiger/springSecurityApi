package study.lms.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect // Aop선언 어노테이션
@Component // 스프링 Bean 등록 어노테이션
public class TimeTraceAop {

    //@After 대상 객체의 메서드를 실행하는 도중 예외가 발생했는지의 여부와 상관없이 메서드 실행후 공통 기능 실행

    @Around(value = "execution(* study.lms.service..*(..))") // 대상 객체의 메서드 실행 전, 후 또는 예외 발생 시점에 공통 기능을 실행
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try{
            return joinPoint.proceed(); //다음 메서드로 진행
        }finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
