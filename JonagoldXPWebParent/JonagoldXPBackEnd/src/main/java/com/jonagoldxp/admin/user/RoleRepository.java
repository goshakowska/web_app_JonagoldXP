package com.jonagoldxp.admin.user;


import com.jonagoldxp.common.entity.Role;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
}
