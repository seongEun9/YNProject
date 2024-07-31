package org.zerock.niceadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String index() {
        return "index"; // templates/index.html 파일을 서빙
    }

    @GetMapping("/example")
    public String example() {
        return "example"; // templates/example.html 파일을 서빙
    }
}