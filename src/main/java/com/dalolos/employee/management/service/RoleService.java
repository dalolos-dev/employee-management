package com.dalolos.employee.management.service;

import com.dalolos.employee.management.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> findAll();
    Optional<Role> findById(Long id);
    Role save(Role role);
    void delete(Long id);
    Role update(Role role);
}
