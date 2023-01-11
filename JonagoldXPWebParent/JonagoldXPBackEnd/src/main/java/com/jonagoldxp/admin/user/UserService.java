package com.jonagoldxp.admin.user;

import com.jonagoldxp.admin.paging.PagingAndSortingHelper;
import com.jonagoldxp.common.entity.Role;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jonagoldxp.common.entity.User;

import java.util.List;

@Service
@Transactional
public class UserService {
    public static final int USERS_PER_PAGE = 4;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    public User getByEmail(String email){
        return userRepo.getUserByEmail(email);
    }

    public List<User> listAll() {
        return (List<User>)  userRepo.findAll(Sort.by("firstName").ascending());

    }

    public void listByPage(int pageNum, PagingAndSortingHelper helper) {
        helper.listEntities(pageNum, USERS_PER_PAGE, userRepo);
    }

    public List<Role> listRoles() {
        return (List<Role>) roleRepo.findAll();
    }

    public void save(User user) {
        userRepo.save(user);
    }

    public boolean isEmailUnique(Integer id, String email) {
        User userByEmail = userRepo.getUserByEmail(email);

        return userByEmail == null;
        /////////////////////////////////////
    }

    public void delete(Integer id){
        userRepo.deleteById(id);
        ///////////////////////////////////
    }
}
