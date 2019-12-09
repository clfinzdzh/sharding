package com.fj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fj.entity.Equip;
import com.fj.mapper.EquipMapper;
import com.fj.service.IEquipService;
import io.shardingjdbc.core.api.HintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equip")
public class EquipController {


    @Autowired
    public IEquipService equipService;
    @Autowired
    private EquipMapper equipMapper;

    @PostMapping("/one")
    public Object saveOne(@RequestBody Equip equip) {
        equip.setEquipId(IdWorker.getId());
        return equipService.save(equip);
    }

    @PostMapping("/batch")
    public Object saveBatch(@RequestBody List<Equip> equips) {
        for (Equip equip : equips) {
            saveOne(equip);
        }
        return 1;
    }

    @GetMapping("/one")
    public Object getOne(Long id) {
        QueryWrapper<Equip> wrapper = new QueryWrapper<>();
        wrapper.eq("equip_id", id);
        return equipService.getOne(wrapper);
    }

    @GetMapping("/batch")
    public Object getList(@RequestParam List<Integer> ids) {
        QueryWrapper<Equip> wrapper = new QueryWrapper<>();
        wrapper.in("equip_id", ids);
        return equipService.list(wrapper);
    }

    @GetMapping("/page")
    public Object getPage(@RequestParam int page, @RequestParam int size) {
        IPage<Equip> pageObject = new Page<>(page, size);
        QueryWrapper<Equip> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("equip_id");
        IPage<Equip> equipIPage1 = equipMapper.selectPage(pageObject, new QueryWrapper<>());
        return equipIPage1;
    }

}
