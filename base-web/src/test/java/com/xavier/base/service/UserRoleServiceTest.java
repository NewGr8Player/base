package com.xavier.base.service;

import com.xavier.base.BaseWebApplication;
import com.xavier.base.service.UserRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BaseWebApplication.class})
public class UserRoleServiceTest {

    @Autowired
    private UserRoleService userRoleService;

    @Test
    public void findByUserId() {
        List list = userRoleService.findByUserId("1");
        System.out.println(list.size());
    }
}