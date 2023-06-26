package com.fish.repository;

import com.fish.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author fish
 * <p>
 */
public interface LogRepository extends JpaRepository<Log, Integer> {
    List<Log> findAll();
}
