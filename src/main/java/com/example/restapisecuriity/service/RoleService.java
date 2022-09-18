package com.example.restapisecuriity.service;

import com.example.restapisecuriity.entity.Role;
import com.example.restapisecuriity.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String role){
        return roleRepository.findByName(role);
    }
}
