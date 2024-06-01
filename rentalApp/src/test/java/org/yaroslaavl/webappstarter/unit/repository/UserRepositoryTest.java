package org.yaroslaavl.webappstarter.unit.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.yaroslaavl.webappstarter.database.entity.User;
import org.yaroslaavl.webappstarter.database.repository.UserRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    private UserRepository userRepository;
    private User user;

    @Test
    @DisplayName("Test for find user by username")
    public void find_user_by_username(){
        String username = "ylopatkin";

        var found = userRepository.findByUsername(username);

        assertTrue(found.isPresent());

        User user = found.get();
        assertEquals(username,user.getUsername());
    }
}
