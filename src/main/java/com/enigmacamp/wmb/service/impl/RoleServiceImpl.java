package com.enigmacamp.wmb.service.impl;

import com.enigmacamp.wmb.entity.Role;
import com.enigmacamp.wmb.repository.RoleRepository;
import com.enigmacamp.wmb.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getOrSave(Role role) {
        //jika sudah ada role kita akn ambil dari database
        Optional<Role> optionalRole = roleRepository.findByName(role.getName());
        if (!optionalRole.isEmpty()){
            return optionalRole.get();
        }
        //jika role belum ada akan create baru
        return roleRepository.save(role);
    }
}
