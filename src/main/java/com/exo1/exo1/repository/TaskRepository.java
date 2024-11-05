package com.exo1.exo1.repository;

import com.exo1.exo1.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT DISTINCT t FROM Task t")
    Page<Task> findAll(Pageable pageable);
}
