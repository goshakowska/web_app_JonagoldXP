package com.jonagoldxp.admin.user;

import com.jonagoldxp.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User getUserByEmail(@Param("email")String email);

    @Query(value = "SELECT u FROM User u WHERE concat(u.id, ' ', u.email, ' ', u.firstName, ' ', u.lastName) LIKE%?1%", nativeQuery = true)
    Page<User> findAll(String keyword, Pageable pageable);
}
