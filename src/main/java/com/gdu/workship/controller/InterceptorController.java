package com.gdu.workship.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InterceptorController {

    @GetMapping("/main")
    public String securedHandler() {
        // 로그인 인터셉터를 통해 로그인 여부를 확인하고, 로그인이 되어있지 않으면 로그인 페이지로 리다이렉트하거나 오류 처리할 수 있습니다.
        // 로그인이 되어있을 경우에는 보안된 페이지의 처리 로직을 수행합니다.
        return "main.do";
    }
}
