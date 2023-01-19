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
    public User get(Integer id) {
            return userRepo.findById(id).get();
    }

    public List<Role> listRoles() {
        return (List<Role>) roleRepo.findAll();
    }

    public User save(User user) {
        return userRepo.save(user);
    }

    public boolean isEmailUnique(Integer id, String email) {
        User userByEmail = userRepo.getUserByEmail(email);

        if (userByEmail == null) return true;

        boolean isCreatingNew = (id == null);

        if (isCreatingNew) {
            if (userByEmail != null) return false;
        } else {
            if (userByEmail.getId() != id) {
                return false;
            }
        }

        return true;
    }

//    public User updateAccount(User userInForm) {
//        User userInDB = userRepo.findById(userInForm.getId()).get();
//
//        if (!userInForm.getPassword().isEmpty()) {
//            userInDB.setPassword(userInForm.getPassword());
//            encodePassword(userInDB);
//        }
//
//        userInDB.setFirstName(userInForm.getFirstName());
//        userInDB.setLastName(userInForm.getLastName());
//
//        return userRepo.save(userInDB);
//    }

//    private void encodePassword(User user) {
//        String encodedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodedPassword);
//    }

    public void delete(Integer id) {
        userRepo.deleteById(id);
    }

    public void updateUserEnabledStatus(Integer id, boolean enabled) {
        userRepo.updateEnabledStatus(id, enabled);
    }
}
