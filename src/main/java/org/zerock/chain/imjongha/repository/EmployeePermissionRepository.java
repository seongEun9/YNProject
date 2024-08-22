package org.zerock.chain.imjongha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.chain.imjongha.model.EmployeePermission;

@Repository
public interface EmployeePermissionRepository extends JpaRepository<EmployeePermission, Long> {
    void deleteByEmployeeEmpNo(Long empNo);
}