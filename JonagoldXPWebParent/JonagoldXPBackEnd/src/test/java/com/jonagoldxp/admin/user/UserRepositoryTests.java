package com.jonagoldxp.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.jonagoldxp.common.entity.Role;
import com.jonagoldxp.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // to run the test on the real database
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager; // class provided by DataJP for unit testing with repository
    @Test
    public void testCreateFirstUser(){
        Role roleAdmin = entityManager.find(Role.class, 1); // id=1 is Admin in our database
        User userExample = new User("example@mail.com", "example1234", "Chupa", "Cabra");
        userExample.addRole(roleAdmin);

        User savedUser = repo.save(userExample);
        assertThat(savedUser.getId()).isGreaterThan(0);

    }
}
