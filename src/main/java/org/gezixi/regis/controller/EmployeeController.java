package org.gezixi.regis.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.gezixi.regis.common.R;
import org.gezixi.regis.config.ParamConstant;
import org.gezixi.regis.model.Employee;
import org.gezixi.regis.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     *
     * @param request 请求
     * @param employee 员工
     * @return 请求体
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        if (emp == null) {
            return R.error("登录失败");
        }
        if (!emp.getPassword().equals(password)) {
            return R.error("账号或密码错误");
        }
        if (emp.getStatus() == 0) {
            return R.error("账号被禁用了");
        }
        request.getSession().setAttribute(ParamConstant.USER_SESSION_NAME, emp.getId());
        return R.success(emp);
    }

    /**
     * 员工退出
     *
     * @param request 请求
     * @return 提示信息
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        // 清理session中的员工信息
        request.getSession().removeAttribute(ParamConstant.USER_SESSION_NAME);
        return R.success("退出成功");
    }

    /**
     * 新增员工
     *
     * @param request 请求
     * @param employee 员工对象
     * @return 提示信息
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        employee.setPassword(DigestUtils.md5DigestAsHex(ParamConstant.DEFAULT_PASSWORD.getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        // 添加操作员工
        Long employeeId = (Long) request.getSession().getAttribute(ParamConstant.USER_SESSION_NAME);
        employee.setCreateUser(employeeId);
        employee.setUpdateUser(employeeId);
        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    /**
     * 员工信息分页查询
     *
     * @param page 数据总数
     * @param pageSize 数据项大小
     * @param name 名称
     * @return Page对象
     */
    @GetMapping("/page")
    public R<Page<Employee>> getPage(int page, int pageSize, String name) {
        Page<Employee> pageInfo = new Page<>(page, pageSize);
        // 构建条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        // 添加过滤条件
        queryWrapper.like(Optional.ofNullable(name).isPresent(), Employee::getName, name);
        // 添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        // 执行查询
        employeeService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }


    /**
     * 编辑员工信息
     *
     * @param request 请求
     * @param employee 员工对象
     * @return 提示信息
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        employeeService.updateById(employee);
        Long curEmployeeId = (Long) request.getSession().getAttribute(ParamConstant.USER_SESSION_NAME);
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(curEmployeeId);
        return R.success("员工信息修改成功");
    }

    /**
     * 编辑员工信息时反填
     *
     * @param id 需要修改的员工id
     * @return 员工对象
     */
    @GetMapping("/{id}")
    public R<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        return R.success(employee);
    }








    }
