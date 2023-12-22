package com.glsi.xpress.Service;

import com.glsi.xpress.Entity.Enum.URole;
import com.glsi.xpress.Entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User updateUser(User user);
    User getUserById(Long userId);
    List<User> getAllUsers();
    void deleteUser(Long userId);
}