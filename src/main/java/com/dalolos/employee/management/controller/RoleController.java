package com.dalolos.employee.management.controller;

import com.dalolos.employee.management.model.Permission;
import com.dalolos.employee.management.model.Role;
import com.dalolos.employee.management.service.PermissionService;
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
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        Optional<Role> role = roleService.findById(id);
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Set<Permission> permissionsList = new HashSet<>();

        if(role.getPermissionsList() != null){
            for (Permission p : role.getPermissionsList()) {
                Permission permission = permissionService.findById(p.getId()).orElseThrow(() -> new IllegalArgumentException("Permission with id " + p.getId() + " not found"));
                permissionsList.add(permission);
            }
        }
        role.setPermissionsList(permissionsList);
        Role savedRole = roleService.save(role);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedRole);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role role) {
        return roleService.findById(id).map(existingRole -> {
            if(role.getRoleName() != null) {
                existingRole.setRoleName(role.getRoleName());
            }
            if(role.getPermissionsList() != null){
                Set<Permission> permissions = new HashSet<>();

                for(Permission p : role.getPermissionsList()) {
                    permissionService.findById(p.getId()).ifPresent(permissions::add);
                }
                existingRole.setPermissionsList(permissions);
            }

            Role updatedRole = roleService.save(existingRole);

            return ResponseEntity.ok(updatedRole);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        return roleService.findById(id).<ResponseEntity<Void>>map(role -> {
            roleService.delete(role.getId());

            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
