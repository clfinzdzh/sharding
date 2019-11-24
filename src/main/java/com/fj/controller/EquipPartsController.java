package com.fj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fj.entity.EquipParts;
import com.fj.service.IEquipPartsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipParts")
@Slf4j
public class EquipPartsController {

    @Autowired
    private IEquipPartsService iEquipPartsService;

    @PostMapping("/one")
    public Object saveOne(@RequestBody EquipParts equipParts) {
        return iEquipPartsService.save(equipParts);
    }

    @PostMapping("/batch")
    public Object saveBatch(@RequestBody List<EquipParts> equipParts) {
        return iEquipPartsService.saveBatch(equipParts);
    }

    @GetMapping("/one")
    public Object getOne(Integer id) {
        QueryWrapper<EquipParts> wrapper = new QueryWrapper<>();
        wrapper.eq("equip_parts_id", id);
        return iEquipPartsService.getOne(wrapper);
    }

    @GetMapping("/batch")
    public Object getList(@RequestParam List<Integer> ids) {
        QueryWrapper<EquipParts> wrapper = new QueryWrapper<>();
        wrapper.in("equip_parts_id", ids);
        return iEquipPartsService.list(wrapper);
    }
}
