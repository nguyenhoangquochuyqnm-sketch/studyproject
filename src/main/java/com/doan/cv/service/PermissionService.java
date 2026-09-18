package com.doan.cv.service;

import com.doan.cv.dto.response.ResultPagination;
import com.doan.cv.entity.Permission;
import com.doan.cv.error.DuplicateValueException;
import com.doan.cv.error.InvalidValueException;
import com.doan.cv.repository.PermissionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;


    public Permission createPermission(@Valid Permission request) {
        if(this.permissionRepository.existsByNameAndApiPathAndMethod(request.getName(),
                                                                     request.getApiPath(),
                                                                     request.getMethod()))
            throw new DuplicateValueException("This permission already existed");

        return this.permissionRepository.save(request);
    }


    public ResultPagination<List<Permission>> getAllPermissions(Pageable pageable) {
        Page<Permission> permissionPage = this.permissionRepository.findAll(pageable);

        List<Permission> permissionList = permissionPage.getContent().stream().toList();

        ResultPagination<List<Permission>> result = new ResultPagination<>();
        ResultPagination.Meta meta = new ResultPagination.Meta();

        meta.setPage(pageable.getPageNumber()+1);
        meta.setPageSize(pageable.getPageSize());
        meta.setTotalPages(permissionPage.getTotalPages());
        meta.setTotalElements(permissionPage.getTotalElements());
        
        result.setResult(permissionList);
        result.setMeta(meta);

        return result;
    }

    public Permission getPermissionById(Long id) {
        return this.permissionRepository.findById(id)
                                        .orElseThrow(() -> new InvalidValueException("permission not found with id: "+id));
    }

    public Permission updatePermission(Long id, @Valid Permission request) {
        Permission existingPermission = this.permissionRepository.findById(id)
                                            .orElseThrow(() -> new InvalidValueException("permission not found with id: "+id));

        if(this.permissionRepository.existsByNameAndApiPathAndMethod(request.getName(),
                                                                     request.getApiPath(),
                                                                     request.getMethod()))
            throw new DuplicateValueException("This permission already existed");

        existingPermission.setName(request.getName());
        existingPermission.setApiPath(request.getApiPath());
        existingPermission.setMethod(request.getMethod());
        existingPermission.setModule(request.getModule());

        return this.permissionRepository.save(existingPermission);
    }

    public void deletePermission(Long id) {
        Permission existingPermission = this.permissionRepository.findById(id)
                                            .orElseThrow(() -> new InvalidValueException("permission not found with id: "+id));

        existingPermission.getRoles().forEach(r -> r.getPermissions().remove(existingPermission));

        this.permissionRepository.delete(existingPermission);
    }
}
