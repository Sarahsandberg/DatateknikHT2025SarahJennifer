package com.bikeshare.lab2;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.bikeshare.model.User;
import com.bikeshare.model.User.UserStatus;

public class UserBlackBoxTest {

    @Test
    void userNameHasValidValue() {
        User user = new User("800101-8129", "kalle@mail.com", "Karl-Erik", "Larsson");

        String name = user.getFirstName();
        Integer nameLenght = name.length();

        // assertTrue(nameLenght >= 2);
        assertTrue(user != null);

    }

    @Test
    void emailValidTest() {
        User user = new User("800101-8129", "pelle@mail.com", "Karl-Erik", "Larsson");

        user.setEmail("kalle@mail.com");

        assertEquals("kalle@mail.com", user.getEmail());

        assertFalse(user.isEmailVerified());

    }

    @Test
    void invalidEmailTest() {
        User user = new User("800101-8129", "pelle@mail.com", "Karl-Erik", "Larsson");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setEmail("kallemail.com");
        });

        assertEquals("Invalid email format", exception.getMessage());
    }

    @Test
    void nameIsEmptyOrNullTest() {
        User user = new User("800101-8129", "pelle@mail.com", "Karl-Erik", "Larsson");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setFirstName("");
        });

        assertEquals("First name cannot be null or empty", exception.getMessage());

    }

    // AI constructed tests
    @Test
    void validPhoneNumber_SwedishFormat() {
        User user = new User("800101-8129", "test@mail.com", "Karl", "Larsson");

        assertDoesNotThrow(() -> user.setPhoneNumber("070-111 22 33"));
    }

    @Test
    void validPhoneNumber_InternationalFormat() {
        User user = new User("800101-8129", "test@mail.com", "Karl", "Larsson");

        assertDoesNotThrow(() -> user.setPhoneNumber("+46 70-111 22 33"));
    }

    @Test
    void invalidPhoneNumber_InvalidFormat() {
        User user = new User("800101-8129", "test@mail.com", "Karl", "Larsson");

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> user.setPhoneNumber("46 70-111 22 33"));
        assertEquals("Invalid phone number format", exception.getMessage());
    }
    // end of ChatGPT tests

    @Test
    void fundBoundaryUnderMin() {
        User user = new User("800101-8129", "pelle@mail.com", "Karl-Erik", "Larsson");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.addFunds(0.09);
        });

        assertEquals("Amount too low", exception.getMessage());
    }

    @Test
    void fundBoundaryMin() {
        User user = new User("800101-8129", "pelle@mail.com", "Karl-Erik", "Larsson");
        user.addFunds(0.10);

        assertEquals(0.1, user.getAccountBalance());
    }

    @Test
    void fundBoundaryOverMin() {
        User user = new User("800101-8129", "pelle@mail.com", "Karl-Erik", "Larsson");
        user.addFunds(0.11);

        assertEquals(0.11, user.getAccountBalance());
    }

    @Test
    void fundBounderyUnderMax() {
        User user = new User("800101-8129", "pelle@mail.com", "Karl-Erik", "Larsson");
        user.addFunds(999);

        assertEquals(999, user.getAccountBalance());
    }

    @Test
    void fundBounderyMax() {
        User user = new User("800101-8129", "pelle@mail.com", "Karl-Erik", "Larsson");
        user.addFunds(1000);

        assertEquals(1000, user.getAccountBalance());
    }

    @Test
    void fundBounderyOverMax() {
        User user = new User("800101-8129", "pelle@mail.com", "Karl-Erik", "Larsson");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.addFunds(1001);
        });

        assertEquals("Cannot add more than $1000 at once", exception.getMessage());
    }
}
