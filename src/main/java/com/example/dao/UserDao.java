package com.example.dao;

import com.example.domain.User;
import com.example.dto.PageAndSortDTO;
import com.example.dto.UserDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by fjc on 2017-07-06.
 */
@Mapper
@Component(value = "userDao")
public interface UserDao {

    List<User> findByCondition(PageAndSortDTO pageAndSortDTO);

    Integer count(PageAndSortDTO pageAndSortDTO);

    User findById(Long id);

    void save(UserDTO userDTO);

    void update(UserDTO userDTO);

    void delete(Long id);

}

