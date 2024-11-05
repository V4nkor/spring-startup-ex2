package com.exo1.exo1.repository;

import com.exo1.exo1.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.task WHERE u.id = :id")
    Optional<User> findByIdWithTask(@Param("id") Long id);

    @Query("SELECT DISTINCT us FROM User us " +
            "LEFT JOIN FETCH us.projets p " +
            "LEFT JOIN FETCH us.task t "
    )
    Page<User> findAll(Pageable pageable);
}
