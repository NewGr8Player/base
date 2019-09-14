package com.xavier.base.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

@SpringBootTest
public class UserRoleServiceTest {

    @Autowired
    private UserRoleService userRoleService;



    @Test
    public void findByUserId() {
        userRoleService.findByUserId("1").forEach(System.out::println);
    }
}