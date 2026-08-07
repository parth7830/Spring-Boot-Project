//package com.JIIT.journalApp.service;
//
//import com.JIIT.journalApp.repository.UserRepository;
//import com.JIIT.journalApp.services.UserDetailsServiceImpl;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.ArrayList;
//
//import static org.mockito.Mockito.*;
//@ActiveProfiles("dev")
//public class UserDetailsServiceImplTests {
//
//    @InjectMocks
//    private UserDetailsServiceImpl userDetailsService;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @BeforeEach
//    void setUp(){
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Disabled
//    @Test
//    void loadUserByUsernameTest(){
//        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn((com.JIIT.journalApp.entity.User) User.builder().username("ram").password("inrinrick").roles(String.valueOf(new ArrayList<>())).build());
//        UserDetails user = userDetailsService.loadUserByUsername("ram");
//        Assertions.assertNotNull(user);
//    }
//}