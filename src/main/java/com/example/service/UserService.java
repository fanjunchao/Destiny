package com.example.service;

import com.example.dao.UserDao;
import com.example.domain.GenderEnum;
import com.example.domain.User;
import com.example.dao.UserRepository;
import com.example.dto.PageAndSortDTO;
import com.example.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by fjc on 2017-06-28.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDao userDao;

    @Transactional
    public String save() {
        for (int i = 0; i < 10; i++) {
//            if (i == 5) {
//                throw new NullPointerException("");
//            }
            User user = new User();
            user.setName("骏超" + i);
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            if (i % 2 == 0) {
                user.setGender(GenderEnum.MAN.getKey());
            } else {
                user.setGender(GenderEnum.WOMAN.getKey());
            }
            user.setAddress("余杭" + i + "路");
            user.setPhone((int) (Math.random() * 1000000000) + i + "");
            userRepository.save(user);
        }
        return "添加成功";
    }

    public Page<User> findByJpa(PageAndSortDTO pageAndSortDTO) {
        final String name = pageAndSortDTO.getName();
        final String address = pageAndSortDTO.getAddress();
        final String gender = pageAndSortDTO.getGender();
        final String start = pageAndSortDTO.getStart();
        final String end = pageAndSortDTO.getEnd();
        final String phone = pageAndSortDTO.getPhone();
        Integer pageNumber = pageAndSortDTO.getPageNumber();
        Integer size = pageAndSortDTO.getSize();
        Sort sort = new Sort(Sort.Direction.DESC, pageAndSortDTO.getSort());
        if (pageAndSortDTO.getDirection() != null) {
            sort = new Sort(pageAndSortDTO.getSort());
        }
        Pageable pageable = new PageRequest(pageNumber, size, sort);
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(name)) {
                    predicates.add(cb.like(root.<String>get("name"), "%" + name + "%"));
                }
                if (!StringUtils.isEmpty(address)) {
                    predicates.add(cb.like(root.<String>get("address"), "%" + address + "%"));
                }
                if (!StringUtils.isEmpty(start) && !StringUtils.isEmpty(end)) {
                    predicates.add(cb.between(root.<String>get("birthday"), start, end));
                }
                if (!StringUtils.isEmpty(gender)) {
                    predicates.add(cb.equal(root.<String>get("gender"), gender));
                }
                if (!StringUtils.isEmpty(phone)) {
                    predicates.add(cb.like(root.<String>get("phone"), "%" + phone + "%"));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return userRepository.findAll(specification, pageable);
    }

    public Map<String, Object> findByMybatis(PageAndSortDTO pageAndSortDTO) {
        Integer size = pageAndSortDTO.getSize();
        pageAndSortDTO.setPageNumber(pageAndSortDTO.getPageNumber() * size);
        List<User> userList = userDao.findByCondition(pageAndSortDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("list", userList);
        map.put("totalElements", userDao.count(pageAndSortDTO));
        map.put("totalPage", Math.ceil((double) userDao.count(pageAndSortDTO) / size));
        return map;
    }

    @Transactional
    public String save(UserDTO userDTO) {
        User user = new User();
        String str = "添加成功";
        if (!StringUtils.isEmpty(userDTO.getId())) {
            str = "修改成功";
        }
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setBirthday(userDTO.getBirthday());
        user.setAddress(userDTO.getAddress());
        user.setGender(userDTO.getGender());
        user.setPhone(userDTO.getPhone());
        userRepository.save(user);
        return str;
    }

    public User edit(Long id) {
//        return userRepository.findOne(id);
        return userDao.findById(id);
    }

    @Transactional
    public String delete(Long[] ids) {
        for (Long id : ids) {
//            userRepository.delete(id);
            userDao.delete(id);
        }
        return "删除成功";
    }

}
