package org.gezixi.regis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.gezixi.regis.mapper.EmployeeMapper;
import org.gezixi.regis.model.Employee;
import org.gezixi.regis.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
