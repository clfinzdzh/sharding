package com.fj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fj.entity.EquipParts;
import com.fj.mapper.EquipPartsMapper;
import com.fj.service.IEquipPartsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Primary
public class EquipPartsServiceImpl extends ServiceImpl<EquipPartsMapper, EquipParts> implements IEquipPartsService {

}
