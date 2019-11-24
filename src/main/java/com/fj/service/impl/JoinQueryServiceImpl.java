package com.fj.service.impl;


import com.fj.entity.JoinQueryEntity;
import com.fj.mapper.JoinQueryMapper;
import com.fj.service.IJoinQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JoinQueryServiceImpl implements IJoinQueryService {

    @Autowired
    private JoinQueryMapper joinQueryMapper;

    @Override
    public List<JoinQueryEntity> queryBaseInfo(List<Integer> equipIds) {
        return joinQueryMapper.queryBaseInfo(equipIds);
    }
}
