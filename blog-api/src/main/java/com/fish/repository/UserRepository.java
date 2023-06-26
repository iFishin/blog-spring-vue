package com.fish.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fish.entity.User;

import java.util.List;

/**
 * @author fish
 * <p>
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByAccount(String account);

}
