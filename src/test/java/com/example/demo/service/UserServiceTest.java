package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
// import org.junit.jupiter.api.Test; // WRONG: unused — @Test was removed from testFindByUserName() because it conflicted with @ParameterizedTest
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.User;
import com.example.demo.repositories.UserRepository;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @ParameterizedTest
    // WRONG: @Test was here alongside @ParameterizedTest — these two annotations conflict;
    // @Test treats the method as a no-arg test and cannot inject the "name" parameter,
    // causing: ParameterResolutionException: No ParameterResolver registered for parameter [String name]
    @CsvSource({
            // WRONG: "ram" was here but user "ram" does not exist in the database, causing assertNotNull to fail
            "ankit",
            "ankit1"
    })
    public void testFindByUserName(String name) {
        // assertEquals(4, 2+2);
        // assertNotNull(userRepository.findByUserName("ankit"));
        // User user = userRepository.findByUserName("ankit");
        // assertTrue(!user.getJournalEntry().isEmpty());
        // assertTrue(5>3);
        assertNotNull(userRepository.findByUserName(name), "Failed for: " + name);
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,9"
    })
    public void test(int a, int b, int expected) {
        assertEquals(expected, a + b);
    }

}
