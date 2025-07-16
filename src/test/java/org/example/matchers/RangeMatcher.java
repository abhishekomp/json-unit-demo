package org.example.matchers;

import net.javacrumbs.jsonunit.core.ParametrizedMatcher;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import java.util.regex.*;
import java.util.*;
public class RangeMatcher extends TypeSafeMatcher<Object> implements ParametrizedMatcher {
    private double min, max;

    @Override
    public void setParameter(String parameter) {
        System.out.printf("[LOG][RangeMatcher] setParameter called: %s%n", parameter);
        // Parse parameter as [min,max] JSON array or simple comma sep
        // Accept "[100,200]" or "100,200"
        String cleaned = parameter.replaceAll("[\\[\\]\"]", "");
        String[] parts = cleaned.split(",");
        min = Double.parseDouble(parts[0].trim());
        max = Double.parseDouble(parts[1].trim());
    }

    @Override
    protected boolean matchesSafely(Object actual) {
        System.out.printf("[LOG][RangeMatcher] matchesSafely called with actual: %s%n", actual);
        double value;
        if (actual instanceof Number) value = ((Number) actual).doubleValue();
        else try { value = Double.parseDouble(actual.toString()); }
             catch (Exception e) { return false; }
        boolean result = value >= min && value <= max;
        System.out.printf("[LOG][RangeMatcher] actual=%.2f, min=%.2f, max=%.2f, result=%b%n", value, min, max, result);
        return result;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a number between ").appendValue(min).appendText(" and ").appendValue(max);
    }

    @Override
    protected void describeMismatchSafely(Object item, Description description) {
        description.appendText("was ").appendValue(item);
    }

    public static RangeMatcher range() {
        return new RangeMatcher();
    }
}