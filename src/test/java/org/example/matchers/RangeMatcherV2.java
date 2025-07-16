package org.example.matchers;

import net.javacrumbs.jsonunit.core.ParametrizedMatcher;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class RangeMatcherV2 extends TypeSafeMatcher<Object> implements ParametrizedMatcher {
    private double min;
    private double max;

    @Override
    public void setParameter(String parameter) {
        System.out.printf("[LOG][RangeMatcher] setParameter called: %s%n", parameter);
        // Accept "[100,400]" or "100,400"
        String cleaned = parameter.replaceAll("[\\[\\]\"]", "");
        String[] parts = cleaned.split(",");
        min = Double.parseDouble(parts[0].trim());
        max = Double.parseDouble(parts[1].trim());
        System.out.printf("[LOG][RangeMatcher] Parameter set: min=%.2f, max=%.2f%n", min, max);
    }

    @Override
    protected boolean matchesSafely(Object actual) {
        System.out.printf("[LOG][RangeMatcher] matchesSafely: %s%n", actual);
        double value;
        try {
            if (actual instanceof Number) value = ((Number) actual).doubleValue();
            else value = Double.parseDouble(actual.toString());
        } catch (Exception e) { return false; }
        boolean isInRange = value >= min && value <= max;
        System.out.printf("[LOG][RangeMatcher] actual=%.2f, in-range=%b%n", value, isInRange);
        return isInRange;
    }

    @Override
    public void describeTo(Description description) {
        System.out.printf("[LOG][RangeMatcher] describeTo called: min=%.2f, max=%.2f%n", min, max);
        description.appendText("a number between ")
                .appendValue(min).appendText(" and ").appendValue(max);
    }

    @Override
    protected void describeMismatchSafely(Object item, Description description) {
    System.out.printf("[LOG][RangeMatcher] describeMismatchSafely called: item=%s%n", item);
        //description.appendText("was ").appendValue(item);
        description.appendText("a number between ")
                .appendText(String.valueOf(min))
                .appendText(" and ")
                .appendText(String.valueOf(max));
    }
}
