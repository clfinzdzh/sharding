package com.fj.controller;

import com.fj.entity.JoinQueryEntity;
import com.fj.service.IJoinQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class JoinQueryController {

    @Autowired
    private IJoinQueryService joinQueryService;

    @GetMapping("/baseInfo")
    public Object queryBaseInfo(@RequestParam("equipIds") List<Integer> equipIds) {
        List<JoinQueryEntity> joinQueryEntities = joinQueryService.queryBaseInfo(equipIds);
        return joinQueryEntities;
    }

}
