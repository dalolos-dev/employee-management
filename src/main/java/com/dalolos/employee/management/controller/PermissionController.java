package com.dalolos.employee.management.controller;

import com.dalolos.employee.management.model.Permission;
import com.dalolos.employee.management.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public ResponseEntity<List<Permission>> getAllPermissions(){
        return ResponseEntity.ok(permissionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable Long id){
        Optional<Permission> permission = permissionService.findById(id);

        return permission.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission){
        Permission savedPermission = permissionService.save(permission);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedPermission);
    }

    @PatchMapping
    public ResponseEntity<Permission> updatePermission(@RequestBody Permission permission){
        Permission savedPermission = permissionService.save(permission);

        return ResponseEntity.ok(savedPermission);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id){
        permissionService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
