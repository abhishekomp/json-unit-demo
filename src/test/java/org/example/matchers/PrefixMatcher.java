package org.example.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class PrefixMatcher extends TypeSafeMatcher<Object> {
    private final String prefix;
    public PrefixMatcher(String prefix) { this.prefix = prefix; }
    @Override
    protected boolean matchesSafely(Object actual) {
        System.out.println("[LOG] PrefixMatcher called. Expected prefix: '" + prefix + "', actual: '" + actual + "'");
        if (actual == null) return false;
        return actual.toString().startsWith(prefix);
    }
    @Override
    public void describeTo(Description description) {
        description.appendText("a string starting with " + prefix);
    }
    @Override
    protected void describeMismatchSafely(Object item, Description description) {
        description.appendText("was ").appendValue(item);
    }
    public static PrefixMatcher prefix(String prefix) { return new PrefixMatcher(prefix); }
}