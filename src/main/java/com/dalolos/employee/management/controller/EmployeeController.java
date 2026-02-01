package com.dalolos.employee.management.controller;

import com.dalolos.employee.management.model.Employee;
import com.dalolos.employee.management.model.Role;
import com.dalolos.employee.management.service.EmployeeService;
import com.dalolos.employee.management.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getAllEmployees() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getEmployeeById(@PathVariable Long id) {
        Optional<Role> role = roleService.findById(id);
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Set<Role> roleList = new HashSet<>();

        employee.setPassword(employeeService.encryptPassword(employee.getPassword()));

        if(employee.getRolesList() != null) {
            for (Role r : employee.getRolesList()) {
                Role role = roleService.findById(r.getId()).orElseThrow(() -> new IllegalArgumentException("Role with id " + r.getId() + " not found"));
                roleList.add(role);
            }
        }
        employee.setRolesList(roleList);
        Employee savedEmployee = employeeService.save(employee);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return employeeService.findById(id).map(existingEmployee -> {
            if (employee.getUsername() != null) {
                existingEmployee.setUsername(employee.getUsername());
            }
            if (employee.getPassword() != null) {
                existingEmployee.setPassword(employee.getPassword());
            }
            if (employee.getRolesList() != null) {
                Set<Role> roles = new HashSet<>();

                for (Role r : employee.getRolesList()) {
                    roleService.findById(r.getId()).ifPresent(roles::add);
                }
                existingEmployee.setRolesList(roles);
            }

            Employee updatedEmployee = employeeService.save(employee);

            return ResponseEntity.ok(updatedEmployee);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        return employeeService.findById(id).<ResponseEntity<Void>>map(employee ->  {
            employeeService.delete(employee.getId());

            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
