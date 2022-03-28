package study.lms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }
}
