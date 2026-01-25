package com.dalolos.employee.management.service;

import com.dalolos.employee.management.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleService roleService;

    @Override
    public List<Role> findAll() {
        return roleService.findAll();
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleService.findById(id);
    }

    @Override
    public Role save(Role role) {
        return roleService.save(role);
    }

    @Override
    public void delete(Long id) {
        roleService.delete(id);
    }

    @Override
    public Role update(Role role) {
        return roleService.save(role);
    }
}
