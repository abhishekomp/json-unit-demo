package org.example.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class UuidHamcrestMatcher extends TypeSafeMatcher<Object> {
    @Override
    protected boolean matchesSafely(Object actual) {
        System.out.println("[LOG] UuidHamcrestMatcher called with: " + actual);
        if (actual == null) return false;
        return actual.toString().matches(
                "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a valid UUID string");
    }

    @Override
    protected void describeMismatchSafely(Object item, Description description) {
        description.appendText("was ").appendValue(item).appendText(" (not a valid UUID)");
    }

    public static UuidHamcrestMatcher uuid() {
        return new UuidHamcrestMatcher();
    }
}