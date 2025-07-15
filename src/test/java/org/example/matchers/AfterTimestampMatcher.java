package org.example.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.time.Instant;

public class AfterTimestampMatcher extends TypeSafeMatcher<Object> {
    private final Instant reference;

    public AfterTimestampMatcher(Instant reference) {
        this.reference = reference;
    }

    @Override
    protected boolean matchesSafely(Object actual) {
        System.out.println("[LOG] AfterTimestampMatcher called, actual: " + actual + ", reference: " + reference);
        if (actual == null) return false;
        try {
            Instant actualTime = Instant.parse(actual.toString());
            return actualTime.isAfter(reference);
        } catch (Exception e) {
            return false;
        }
    }
    @Override
    public void describeTo(Description description) {
        description.appendText("a timestamp after " + reference.toString());
    }
    @Override
    protected void describeMismatchSafely(Object item, Description description) {
        description.appendText("was ").appendValue(item).appendText(", not after ").appendValue(reference);
    }
    public static AfterTimestampMatcher after(Instant ref) {
        return new AfterTimestampMatcher(ref);
    }
}