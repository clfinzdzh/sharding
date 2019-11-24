package com.fj.service;

import com.fj.entity.JoinQueryEntity;

import java.util.List;

public interface IJoinQueryService {

    List<JoinQueryEntity> queryBaseInfo(List<Integer> equipIds);

}
