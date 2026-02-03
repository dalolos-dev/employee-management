package com.dalolos.employee.management.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/proof")
@PreAuthorize("denyAll()")
public class ProofController {
    @GetMapping("/with_auth")
    @PreAuthorize("hasRole('ADMIN')")
    public String proofWithAuth() {
        return "Successful access -> WITH AUTH";
    }

    @GetMapping("/without_auth")
    @PreAuthorize("permitAll()")
    public String proofWithoutAuth() {
        return "Successful access -> WITHOUT AUTH";
    }
}
