package com.fj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fj.entity.Equip;
import com.fj.service.IEquipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equip")
public class EquipController {

    @Autowired
    public IEquipService equipService;

    @PostMapping("/one")
    public Object saveOne(@RequestBody Equip equip) {
        return equipService.save(equip);
    }

    @PostMapping("/batch")
    public Object saveBatch(@RequestBody List<Equip> equips) {
        return equipService.saveBatch(equips);
    }

    @GetMapping("/one")
    public Object getOne(Integer id) {
        QueryWrapper<Equip> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        return equipService.getOne(wrapper);
    }

    @GetMapping("/batch")
    public Object getList(@RequestParam List<Integer> ids) {
        QueryWrapper<Equip> wrapper = new QueryWrapper<>();
        wrapper.in("id", ids);
        return equipService.list(wrapper);
    }

}
