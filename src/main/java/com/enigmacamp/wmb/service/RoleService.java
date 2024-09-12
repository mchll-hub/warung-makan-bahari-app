package com.enigmacamp.wmb.service;

import com.enigmacamp.wmb.entity.Role;

public interface RoleService {
    Role getOrSave(Role role);
}
