package com.jonagoldxp.admin.user;

import com.jonagoldxp.common.entity.Role;
import com.jonagoldxp.common.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserService service;
    @MockBean
    private UserRepository userRepo;
    @MockBean
    private RoleRepository roleRepo;

    @Test
    public void getAllUsersTest(){
        when(userRepo.findAll(Sort.by("firstName").ascending())).thenReturn(List.of(new User(), new User()));
        assertEquals(2, service.listAll().size());
    }

    @Test
    public void getAllRolesTest(){
        when(roleRepo.findAll()).thenReturn(List.of(new Role(), new Role(), new Role()));
        assertEquals(3, service.listRoles().size());
    }

    @Test
    public void getUsersByIdTest(){
        Integer id = 1;
        User user= new User(id);
        when(userRepo.findById(id)).thenReturn(Optional.of(user));
        assertEquals(user, service.get(id));
    }

    @Test
    public void getUsersByEmailTest(){
        String email = "email@mail.com";
        User user= new User(email, "password", "name", "surname");
        when(userRepo.getUserByEmail(email)).thenReturn(user);
        assertEquals(user, service.getByEmail(email));
    }

    @Test
    public void deleteUserTest(){
        Integer id = 2;
        service.delete(id);
        Mockito.verify(userRepo, times(1)).deleteById(id);
    }

    @Test
    public void saveUserTest(){
        User user = new User();
        when(userRepo.save(user)).thenReturn(user);

        assertEquals(user, service.save(user));
    }

    @Test
    public void checkUniqueEmailFailTest(){
        Integer id = 420;
        String email = "email@mail.com";
        User user = new User(id);
        user.setEmail(email);

        when(userRepo.getUserByEmail(email)).thenReturn(user);

        assert !service.isEmailUnique(2137, email);
    }

    @Test
    public void checkUniqueEmailTest(){
        Integer id = 420;
        String email = "email@mail.com";
        User user = new User(id);
        user.setEmail(email);

        when(userRepo.getUserByEmail(email)).thenReturn(null);

        assert service.isEmailUnique(id, email);
    }
}
