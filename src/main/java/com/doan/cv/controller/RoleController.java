package com.doan.cv.controller;

import com.doan.cv.annotation.APImessage;
import com.doan.cv.dto.response.ResultPagination;
import com.doan.cv.entity.Role;
import com.doan.cv.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    @APImessage("role created")
    public ResponseEntity<Role> createRole(@Valid @RequestBody Role request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(request));
    }

    @GetMapping("/{id}")
    @APImessage("fetch role by ID")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @GetMapping
    @APImessage("fetch all roles")
    public ResponseEntity<ResultPagination<List<Role>>> getAllRoles(@RequestParam(name = "current", defaultValue = "1") int currentPage,
                                                                    @RequestParam(name = "size", defaultValue = "10") int pageSize) {
        if (currentPage < 1)
            currentPage = 1;
        if (pageSize < 1 || pageSize > 100)
            pageSize = 10;

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

        return ResponseEntity.ok(roleService.getAllRoles(pageable));
    }

    @PutMapping("/{id}")
    @APImessage("update role")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @Valid @RequestBody Role request) {
        return ResponseEntity.ok(roleService.updateRole(id, request));
    }

    @DeleteMapping("/{id}")
    @APImessage("deleted a role")
    public ResponseEntity<Void> deletedRole(@PathVariable Long id){
        this.roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}