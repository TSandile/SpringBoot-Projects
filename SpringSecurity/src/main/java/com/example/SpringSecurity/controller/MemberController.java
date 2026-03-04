package com.example.SpringSecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/members")
public class MemberController {

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public String getAllMembers(){
        return "You receive all members";
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveMember(){
        return "You save a Member";
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DEVELOPER')")
    public String updateMember(){
        return "You update a Member";
    }
}
