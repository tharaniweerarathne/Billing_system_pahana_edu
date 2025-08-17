package com.Billing_system_pahana_edu.service;

import com.Billing_system_pahana_edu.dao.UserDAO;
import com.Billing_system_pahana_edu.dto.UserDTO;
import com.Billing_system_pahana_edu.model.User;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AuthServiceTest {

    private AuthService authService;

    class StubUserDAO extends UserDAO {
        private final List<User> dummyUsers = new ArrayList<>();

        public StubUserDAO() {
            dummyUsers.add(new User.Builder()
                    .setId("S001")
                    .setName("Test")
                    .setEmail("test@gmail.com")
                    .setUsername("test")
                    .setPassword("test123")
                    .setRole("Staff")
                    .build());

            dummyUsers.add(new User.Builder()
                    .setId("S002")
                    .setName("Tharani")
                    .setEmail("tharani@gmail.com")
                    .setUsername("tharani")
                    .setPassword("pass2")
                    .setRole("Staff")
                    .build());
        }

        @Override
        public User login(String username, String password) {
            return dummyUsers.stream()
                    .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                    .findFirst().orElse(null);
        }
    }

    @Before
    public void setup() throws Exception {
        authService = new AuthService();

        Field daoField = AuthService.class.getDeclaredField("dao");
        daoField.setAccessible(true);
        daoField.set(authService, new StubUserDAO());
    }

    @Test
    public void testLoginSuccess() {
        UserDTO dto = new UserDTO("test", "test123");
        User user = authService.login(dto);

        assertNotNull(user);
        assertEquals("Test", user.getName());
    }

    @Test
    public void testLoginSuccessSecondUser() {
        UserDTO dto = new UserDTO("tharani", "pass2");
        User user = authService.login(dto);

        assertNotNull(user);
        assertEquals("Tharani", user.getName());
    }

    @Test
    public void testLoginFailure() {
        UserDTO dto = new UserDTO("test", "wrongpass");
        User user = authService.login(dto);

        assertNull(user);
    }
}
