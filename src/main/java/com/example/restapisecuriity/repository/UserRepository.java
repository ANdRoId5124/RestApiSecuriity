package com.example.restapisecuriity.repository;

import com.example.restapisecuriity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByLogin(String login);
}
