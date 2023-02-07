package org.gezixi.regis.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.gezixi.regis.common.R;
import org.gezixi.regis.model.Category;
import org.gezixi.regis.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody Category category) {
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    /**
     * 分页查询
     *
     * @param page 页数
     * @param pageSize total页
     * @return page页
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {
        Page<Category> pageInfo = new Page<> (page, pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }


    /**
     * 根据id删除分类
     * @param id
     * @return a
     */
    @DeleteMapping
    public R<String> delete(Long id){
        //categoryService.removeById(id);
        categoryService.removeById(id);

        return R.success("分类信息删除成功");
    }

    /**
     * 根据id修改分类信息
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("修改分类信息：{}",category);

        categoryService.updateById(category);

        return R.success("修改分类信息成功");
    }

}
