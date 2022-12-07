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
    public void testCreateUserWithOneRole(){
        Role roleAdmin = entityManager.find(Role.class, 1); // id=1 is Admin in our database
        User userExample = new User("example@mail.com", "example1234", "Chupa", "Cabra");
        userExample.addRole(roleAdmin);

        User savedUser = repo.save(userExample);
        assertThat(savedUser.getId()).isGreaterThan(0);

    }

    @Test
    public void testCreateNewUserWithManyRoles() {
        User userAlmighty = new User("al@mighty.com", "thebest1", "Al", "Mighty");

        Role roleEditor = new Role(3); // editor
        Role roleAssistant = new Role(5); // assistant

        userAlmighty.addRole(roleEditor);
        userAlmighty.addRole(roleAssistant);

        User savedUser = repo.save(userAlmighty);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers() {
        Iterable<User> listUsers = repo.findAll(); // method that returns all users of the database
        listUsers.forEach(System.out::println);
    }

    @Test
    public void testGetUserById() {
        User userChupa = repo.findById(1).get();
        System.out.println(userChupa);
        assertThat(userChupa).isNotNull();
    }

    @Test
    public void testUpdateUserDetails() {
        User userChupa = repo.findById(1).get();
        userChupa.setEnabled(true);
        userChupa.setEmail("angry_chupa@cabra.com");

        repo.save(userChupa);
    }

    @Test
    public void testUpdateUserRoles() {
        User userAlmighty = repo.findById(2).get();
        Role roleEditor = new Role(3);
        Role roleSalesperson = new Role(2);

        userAlmighty.getRoles().remove(roleEditor);
        userAlmighty.getRoles().add(roleSalesperson);

        repo.save(userAlmighty);
    }
}
