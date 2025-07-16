package org.example.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class EmailMatcher extends TypeSafeMatcher<Object> {
    @Override
    protected boolean matchesSafely(Object actual) {
        if (actual == null) {
            return false;
        }
        if (actual instanceof String) {
            String email = actual.toString();
            // Regex for validating email format
            return email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a valid email address");
    }

    @Override
    protected void describeMismatchSafely(Object item, Description description) {
        description.appendText("was ").appendValue(item)
                   .appendText(", which is not a valid email address");
    }

    public static EmailMatcher email() {
        return new EmailMatcher();
    }
}