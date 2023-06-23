package com.fish.repository;

import com.fish.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author fish
 * <p>
 * 2018年4月18日
 */
public interface LogRepository extends JpaRepository<Log, Integer> {
}
