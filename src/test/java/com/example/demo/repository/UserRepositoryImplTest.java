package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test; // FIX: added @Test import so JUnit recognizes testSaveNewUser() as a test
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.repositories.UserRepositoryImpl;

@SpringBootTest
public class UserRepositoryImplTest {


    @Autowired
    private UserRepositoryImpl userRepositoryImpl;


    // @Disabled
    // @ParameterizedTest
    // @CsvSource({
    //         "1,1,2",
    //         "2,10,12",
    //         "3,3,9"
    // })
    @Test // FIX: added so JUnit picks up this method and actually runs it
    public void testSaveNewUser() {
       userRepositoryImpl.getUserForSA();
    }

}
