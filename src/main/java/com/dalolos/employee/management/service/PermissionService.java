package com.dalolos.employee.management.service;

import com.dalolos.employee.management.model.Permission;

import java.util.List;
import java.util.Optional;

public interface PermissionService {
    List<Permission> findAll();
    Optional<Permission> findById(Long id);
    Permission save(Permission permission);
    void delete(Long id);
    Permission update(Permission permission);
}
