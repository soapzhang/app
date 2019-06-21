package com.soap.app.mapper;

import com.soap.app.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    void addUser(User u);

    void updateUser(User u);

    void deleteUser(int id);

    List<User> getUsers(List<Integer> ids);
}
