package com.dalolos.employee.management.service;

import com.dalolos.employee.management.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeService employeeService;

    @Override
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return employeeService.findById(id);
    }

    @Override
    public Employee save(Employee employee) {
        return employeeService.save(employee);
    }

    @Override
    public void delete(Long id) {
        employeeService.delete(id);
    }

    @Override
    public Employee update(Employee employee) {
        return employeeService.save(employee);
    }
}
