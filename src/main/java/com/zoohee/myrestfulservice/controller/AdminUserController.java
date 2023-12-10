package com.zoohee.myrestfulservice.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.zoohee.myrestfulservice.bean.AdminUser;
import com.zoohee.myrestfulservice.bean.User;
import com.zoohee.myrestfulservice.dao.UserDaoService;
import com.zoohee.myrestfulservice.exception.UserNotFoundException;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.apache.catalina.mbeans.MBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/admin")
public class AdminUserController {
    private UserDaoService service;

    public AdminUserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users/{id}")
    public MappingJacksonValue retrieveAllUser4Admin(@PathVariable int id) {
        User user = service.findOne(id);

        AdminUser adminUser = new AdminUser();

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            // source, target
            BeanUtils.copyProperties(user, adminUser);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joindate", "ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers4Admin() {
        List<User> users = service.findAll();

        List<AdminUser> adminUsers = new ArrayList<>();
        AdminUser adminUser = null;

        for(User user : users) {
            adminUser = new AdminUser();
            BeanUtils.copyProperties(user, adminUser);
            adminUsers.add(adminUser);
        }


        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUsers);
        mapping.setFilters(filters);

        return mapping;
    }

}
