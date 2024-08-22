package org.zerock.chain.junhyuck.repository;

import org.zerock.chain.junhyuck.model.Signup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SignupRepository extends JpaRepository<Signup, Long> {
    Optional<Signup> findByEmpNo(Long empNo);
}