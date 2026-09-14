package com.doan.cv.repository;

import com.doan.cv.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    boolean existsByNameAndApiPathAndMethod(String name, String apiPath, String method);

    List<Permission> findByPermissionIdIn(List<Long> idList);
}
