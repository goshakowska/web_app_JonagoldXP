package com.jonagoldxp.admin.user;

import com.jonagoldxp.common.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
