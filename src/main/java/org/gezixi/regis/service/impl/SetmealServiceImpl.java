package org.gezixi.regis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.gezixi.regis.mapper.SetmealMapper;
import org.gezixi.regis.model.Setmeal;
import org.gezixi.regis.service.SetmealService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}
