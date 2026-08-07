//package com.JIIT.journalApp.service;
//
//import com.JIIT.journalApp.entity.User;
//import com.JIIT.journalApp.repository.UserRepository;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@SpringBootTest
//public class UserServiceTests {
//    @Autowired
//    private UserRepository userRepository;
//    @Disabled
//    @Test
//    public void testFindByUserName(){
//        User user = userRepository.findByUserName("New");
//        assertTrue(!user.getJournalEntries().isEmpty());
//    }
//    @ParameterizedTest
//    @CsvSource({
//            "1,1,2",
//            "2,10,12",
//            "3,3,9"
//    })
//    public void test(int a,int c,int expected){
//        assertEquals(expected,a+c);
//    }
//}
