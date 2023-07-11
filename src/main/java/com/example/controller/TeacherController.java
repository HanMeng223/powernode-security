package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/teacher")
public class TeacherController {
    @GetMapping("/query")
    @PreAuthorize("hasAuthority('teacher:query')")
    public String queryInfo(){
        return "I am a teacher!";
    }
}
