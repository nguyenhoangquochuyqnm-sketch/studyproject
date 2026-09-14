package com.doan.cv.service;

import com.doan.cv.dto.response.ResultPagination;
import com.doan.cv.entity.Permission;
import com.doan.cv.entity.Role;
import com.doan.cv.error.DuplicateValueException;
import com.doan.cv.error.InvalidValueException;
import com.doan.cv.repository.PermissionRepository;
import com.doan.cv.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    public Role createRole(Role request) {
        if (this.roleRepository.existsByName(request.getName()))
            throw new DuplicateValueException("This role already existed");

        List<Permission> permissions = null;

        if ((request.getPermissions() != null)){
            permissions = this.permissionRepository.findByPermissionIdIn(request.getPermissions().stream()
                                                                                       .map(Permission::getPermissionId)
                                                                                       .collect(Collectors.toList())   );
        }
        request.setPermissions(permissions);

        return this.roleRepository.save(request);
    }

    public ResultPagination<List<Role>> getAllRoles(Pageable pageable) {
        Page<Role> rolePage = this.roleRepository.findAll(pageable);

        List<Role> roleList = rolePage.getContent().stream().toList();

        ResultPagination<List<Role>> result = new ResultPagination<>();
        ResultPagination.Meta meta = new ResultPagination.Meta();

        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setTotalPages(rolePage.getTotalPages());
        meta.setTotalElements(rolePage.getTotalElements());

        result.setResult(roleList);
        result.setMeta(meta);

        return result;
    }

    public Role getRoleById(Long id) {
        return this.roleRepository.findById(id)
                                  .orElseThrow(() -> new InvalidValueException("role not found with id: " + id));
    }

    public Role updateRole(Long id, Role request) {
        Role existingRole = this.roleRepository.findById(id)
                .orElseThrow(() -> new InvalidValueException("role not found with id: " + id));

        existingRole.setName(request.getName());
        existingRole.setDescription(request.getDescription());

        List<Permission> permissions = null;

        if (request.getPermissions() != null) {
            permissions = this.permissionRepository.findByPermissionIdIn(request.getPermissions().stream()
                                                                                                 .map(Permission::getPermissionId)
                                                                                                 .collect(Collectors.toList())
            );
        }
        existingRole.setPermissions(permissions);

        return this.roleRepository.save(existingRole);
    }

    public void deleteRole(Long id) {
        Role existingRole = this.roleRepository.findById(id)
                                               .orElseThrow(() -> new InvalidValueException("role not found with id: " + id));
        this.roleRepository.delete(existingRole);
    }
}