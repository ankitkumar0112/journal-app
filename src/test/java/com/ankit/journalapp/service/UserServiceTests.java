package com.ankit.journalapp.service;

import com.ankit.journalapp.entity.UserDataModel;
import com.ankit.journalapp.exception.AlreadyExistsException;
import com.ankit.journalapp.exception.DataNotFoundException;
import com.ankit.journalapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    private UserDataModel testUser;

    @BeforeEach
    void setUp() {
        testUser = UserDataModel.builder()
                .id(UUID.fromString("e1d7665b-dfdf-4dd0-8f39-7162d819ec31"))
                .userName("testuser")
                .password("Password")
                .roles(new ArrayList<>(Arrays.asList("Admin", "Manager")))
                .build();
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(testUser));

        var result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("testuser", result.getFirst().getUserName());
    }

    @Test
    void testFindByUserNameSuccess() {
        when(userRepository.findByUserName("testuser")).thenReturn(testUser);

        UserDataModel user = userService.findByUserName("testuser");

        assertNotNull(user);
        assertEquals("testuser", user.getUserName());
    }

    @Test
    void testFindByUserNameNotFound() {
        when(userRepository.findByUserName("notfound")).thenReturn(null);

        assertThrows(DataNotFoundException.class, () -> userService.findByUserName("notfound"));
    }

    @Test
    void testCreateUserSuccess() {
        when(userRepository.findByUserName("testuser")).thenReturn(null);
        when(userRepository.save(any(UserDataModel.class))).thenReturn(testUser);

        userService.createUser(testUser);

        verify(userRepository, times(1)).save(any(UserDataModel.class));
    }

    @Test
    void testCreateUserAlreadyExists() {
        when(userRepository.findByUserName("testuser")).thenReturn(testUser);

        assertThrows(AlreadyExistsException.class, () -> userService.createUser(testUser));
    }

    @Test
    void testAssignJournalToAUser() {
        userService.assignJournalToAUser(testUser);

        verify(userRepository, times(1)).saveAndFlush(testUser);
    }

    @Test
    void testUpdateUser() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testuser");

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);

        when(userRepository.findByUserName("testuser")).thenReturn(testUser);

        testUser.setUserName("updateduser");
        testUser.setPassword("updatedpassword");
        userService.updateUser(testUser);

        verify(userRepository).save(any(UserDataModel.class));
    }

    @Test
    void testDeleteByUserName() {
        userService.deleteByUserName("testuser");

        verify(userRepository, times(1)).deleteByUserName("testuser");
    }
}