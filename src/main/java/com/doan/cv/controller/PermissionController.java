package com.doan.cv.controller;

import com.doan.cv.annotation.APImessage;
import com.doan.cv.dto.response.ResultPagination;
import com.doan.cv.entity.Permission;
import com.doan.cv.service.PermissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping
    @APImessage("permission created")
    public ResponseEntity<Permission> createPermission(@Valid @RequestBody Permission request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(permissionService.createPermission(request));
    }

    @GetMapping("/{id}")
    @APImessage("fetch permission by ID")
    public ResponseEntity<Permission> getPermissionById(@PathVariable Long id) {
        return ResponseEntity.ok(permissionService.getPermissionById(id));
    }

    @GetMapping
    @APImessage("fetch all permissions")
    public ResponseEntity<ResultPagination<List<Permission>>> getAllPermissions(@RequestParam(name = "current", defaultValue = "1") int currentPage,
                                                                                @RequestParam(name = "size", defaultValue = "10") int pageSize) {
        if (currentPage < 1)
            currentPage = 1;
        if (pageSize < 1 || pageSize > 100)
            pageSize = 10;

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

        return ResponseEntity.ok(permissionService.getAllPermissions(pageable));
    }

    @PutMapping("/{id}")
    @APImessage("update permission")
    public ResponseEntity<Permission> updatePermission(@PathVariable Long id, @Valid @RequestBody Permission request) {
        return ResponseEntity.ok(permissionService.updatePermission(id, request));
    }

    @DeleteMapping("/{id}")
    @APImessage("delete permission")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.noContent().build();
    }
}