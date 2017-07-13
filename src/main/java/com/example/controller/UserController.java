package com.example.controller;

import com.example.dao.UserDao;
import com.example.domain.*;
import com.example.dto.PageAndSortDTO;
import com.example.dto.UserDTO;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by fjc on 2017-06-28.
 */
@RestController
@RequestMapping("/demo")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    /**
     * 批量添加数据
     * @return
     */
    @GetMapping("/save")
    public String save() {
        return userService.save();
    }

    /**
     * Mybatis查询
     * @param pageAndSortDTO
     * @return
     */
    @PostMapping("/findByMybatis")
    public Map<String, Object> findByMybatis(@RequestBody PageAndSortDTO pageAndSortDTO) {
        return userService.findByMybatis(pageAndSortDTO);
    }

    /**
     * JPA查询
     * @param pageAndSortDTO
     * @return
     */
    @PostMapping("/findByJpa")
    public Page<User> findByJpa(@RequestBody PageAndSortDTO pageAndSortDTO) {
        return userService.findByJpa(pageAndSortDTO);
    }

    /**
     * 新增
     * @param userDTO
     * @return
     */
    @PostMapping("/save")
    public String save(@RequestBody UserDTO userDTO) {
        return userService.save(userDTO);
//        userDao.save(userDTO);
//        return "保存成功";
    }

    /**
     * JPA修改
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    public User edit(@PathVariable Long id) {
        return userService.edit(id);
    }

    /**
     * Mybatis修改
     * @param userDTO
     * @return
     */
    @PostMapping(value = "/update")
    public String update(@RequestBody UserDTO userDTO) {
        userDao.update(userDTO);
        return "修改成功";
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @PostMapping("/del")
    public String del(@RequestParam("ids[]") Long[] ids) {
        return userService.delete(ids);
    }

//    @PostMapping("/del")
//    public String del(@RequestBody Map<String, Long[]> ids) {
//        Long[] id = ids.get("ids");
//        return userService.delete(id);
//    }
}
