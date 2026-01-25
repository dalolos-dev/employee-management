package com.dalolos.employee.management.service;

import com.dalolos.employee.management.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionService permissionService;

    @Override
    public List<Permission> findAll() {
        return permissionService.findAll();
    }

    @Override
    public Optional<Permission> findById(Long id) {
        return permissionService.findById(id);
    }

    @Override
    public Permission save(Permission permission) {
        return permissionService.save(permission);
    }

    @Override
    public void delete(Long id) {
        permissionService.delete(id);
    }

    @Override
    public Permission update(Permission permission) {
        return permissionService.save(permission);
    }
}
