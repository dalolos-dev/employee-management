package com.dalolos.employee.management.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/proof")
public class ProofController {
    @GetMapping("/with_auth")
    public String proofWithAuth() {
        return "Successful access -> WITH AUTH";
    }

    @GetMapping("/without_auth")
    public String proofWithoutAuth() {
        return "Successful access -> WITHOUT AUTH";
    }
}
