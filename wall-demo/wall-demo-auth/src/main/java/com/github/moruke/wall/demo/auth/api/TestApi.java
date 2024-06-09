package com.github.moruke.wall.demo.auth.api;

import com.github.moruke.wall.auth.annotation.Judge;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestApi {
    @Judge(subjectId = 1, objectName = "test", actionName = "test", domainId = 1)
    @RequestMapping("/test")
    public String test(){
        return "test";
    }
}
