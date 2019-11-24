package com.fj.mapper;

import com.fj.entity.JoinQueryEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JoinQueryMapper {

    List<JoinQueryEntity> queryBaseInfo(@Param("equipIds") List<Integer> equipIds);

}
