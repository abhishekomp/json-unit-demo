package org.example.matchers;

import org.junit.jupiter.api.Test;

import static org.example.matchers.EmailMatcher.email;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EmailMatcherTest {

    @Test
    public void testValidEmail() {
        assertThat("test@example.com", email());
    }

    @Test
    public void testInvalidEmail() {
        // Negative test case
        //assertThat("invalid-email", email());
        AssertionError assertionError = assertThrows(AssertionError.class, () -> assertThat("invalid-email", email()));
        assertTrue(assertionError.getMessage().contains("was \"invalid-email\", which is not a valid email address"));
    }
}