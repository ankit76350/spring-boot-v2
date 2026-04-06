package com.example.demo.service;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

// import org.mockito.ArgumentMatcher; // WRONG: unused import, removed
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
// import org.mockito.InjectMocks; // WRONG: not needed with @SpringBootTest; @InjectMocks is for pure Mockito tests (no Spring context)
// import org.mockito.Mock; // WRONG: not needed with @SpringBootTest; use @MockBean instead to register mocks in Spring context
// import org.mockito.junit.jupiter.MockitoExtension; // WRONG: not needed with @SpringBootTest; only used with @ExtendWith for pure Mockito tests
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
// import org.springframework.beans.factory.annotation.Autowired; // WRONG: unused — @Autowired is only needed with @SpringBootTest, which is currently commented out
// import org.springframework.boot.test.context.SpringBootTest; // WRONG: unused — @SpringBootTest is currently commented out (pure Mockito test)
// import org.springframework.boot.test.mock.mockito.MockBean; // WRONG: unused — @MockBean is only for @SpringBootTest; pure Mockito tests use @Mock instead
// import org.springframework.beans.factory.annotation.Autowired; // WRONG: @Autowired needs Spring context; use @InjectMocks for Mockito unit tests
// import org.springframework.security.core.userdetails.User; // WRONG: repository returns com.example.demo.entity.User, not Spring Security's User
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserDetailsServiceImpl;

import org.junit.jupiter.api.BeforeEach;

// import net.bytebuddy.asm.Advice.Argument; // WRONG: bytebuddy is a low-level library not meant for use in tests

import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith; // WRONG: not needed with @SpringBootTest; @ExtendWith(MockitoExtension.class) is only for pure Mockito tests

// import java.util.ArrayList; // WRONG: unused import — ArrayList was only needed for the incorrect User.builder() line which is now commented out
import java.util.List;

// @SpringBootTest // Commented out: using pure Mockito approach with @InjectMocks + @Mock instead
public class UserServiceImpTest {

    // @Autowired // WRONG: @Autowired is for Spring context; Mockito unit tests do not load Spring context
    @InjectMocks // CORRECT: injects the real UserDetailsServiceImpl and injects @Mock fields into it automatically
    private UserDetailsServiceImpl userDetailsService;

    @Mock // CORRECT: pure Mockito mock, used with @InjectMocks (not @SpringBootTest)
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        // MockitoAnnotations.initMocks(this); // WRONG: initMocks() is deprecated since Mockito 3.4+
        MockitoAnnotations.openMocks(this); // CORRECT: openMocks() is the non-deprecated replacement
    }


    @Test
    void loadUserByUsernameTest() {
        // WRONG (your original line):
        // when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("ram").password("inrinrick").builder());
        // Problems:
        //   1. Used Spring Security's User.builder(), but repository returns com.example.demo.entity.User
        //   2. Spring Security's builder uses .username() not .userName()
        //   3. .builder() at the end should be .build()

        // CORRECT: Create your entity User (the type that UserRepository.findByUserName() actually returns)
        User mockUser = new User("ram", "inrinrick");
        mockUser.setRoles(List.of("USER"));

        // WRONG: User entity only has @Data (no @Builder), so User.builder() will not compile
        // when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("ram").password("inrinrick").roles(new ArrayList<>()).build());
        // CORRECT: use the mockUser object already created above
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(mockUser);

        UserDetails user = userDetailsService.loadUserByUsername("ram");

        assertNotNull(user);
        assertEquals("ram", user.getUsername());
    }
}
