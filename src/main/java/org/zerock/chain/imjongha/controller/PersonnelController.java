package org.zerock.chain.imjongha.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/personnel")
@Log4j2
public class PersonnelController {


    // 사원 관리 페이지
    @GetMapping("/management")
    public String management() {
        return "admin/personnel/management";
    }

    // 사원 태스트 페이지
    @GetMapping("/managemant")
    public String managemant() {
        return "admin/personnel/managemeat";
    }


    // 권한 관리 페이지
    @GetMapping("/powerSetting")
    public String powerSetting() {
        return "admin/personnel/powerSetting";
    }


    @GetMapping("/RanksRole")
    public String ranksSetting() {return "admin/personnel/RanksRole";}
}