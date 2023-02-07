package org.gezixi.regis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.gezixi.regis.model.Category;

public interface CategoryService extends IService<Category> {
    void remove(Long id);
}
