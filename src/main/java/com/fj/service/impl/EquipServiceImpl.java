package com.fj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fj.entity.Equip;
import com.fj.mapper.EquipMapper;
import com.fj.service.IEquipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Primary
public class EquipServiceImpl extends ServiceImpl<EquipMapper, Equip> implements IEquipService {


}
