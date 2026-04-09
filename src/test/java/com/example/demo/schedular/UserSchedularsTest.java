package com.example.demo.schedular;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.scheduler.UserSchedular;

@SpringBootTest
public class UserSchedularsTest {
    @Autowired
    private UserSchedular userSchedular;

    @Test
    public void testFetchUsersAndSendSaMail() {
        userSchedular.fetchUsersAndSendSaMail();
    }
}
