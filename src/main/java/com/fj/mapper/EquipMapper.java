package com.fj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fj.entity.Equip;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipMapper extends BaseMapper<Equip> {

    List<Equip> selectPageResult(@Param("start") long start, @Param("size") long size);
}
