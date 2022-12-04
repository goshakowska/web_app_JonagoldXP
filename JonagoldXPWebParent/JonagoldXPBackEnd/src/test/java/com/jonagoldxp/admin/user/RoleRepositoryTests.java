package com.jonagoldxp.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.jonagoldxp.common.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // to test a real (and not in-memory) database
public class RoleRepositoryTests {
    @Autowired
    private RoleRepository repo;

    @Test
    public void testCreateFirstRole(){
        Role roleAdmin = new Role("Admin", "manage web application");
        Role savedRole = repo.save(roleAdmin); // returns persistent Role object
        assertThat(savedRole.getId()).isGreaterThan(0); // indicates that Role object has been persisted into the db

    }

}
