package org.example.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class OrderDateMatcher extends TypeSafeMatcher<Object> {
    @Override
    protected boolean matchesSafely(Object actual) {
        System.out.println("[LOG] OrderDateMatcher called with: " + actual);
        // parse the actual date string as LocalDate
        if (actual == null) {
            return false;
        } else // If it's already a LocalDate, we can consider it valid
            if (actual instanceof String) {
            String dateStr = actual.toString();
            // Check if the date string is in the format "yyyy-MM-dd"
            return dateStr.matches("\\d{4}-\\d{2}-\\d{2}");
        } else return actual instanceof java.time.LocalDate;
    }

    @Override
    public void describeTo(Description description) {

        //description.appendText(" a valid order date");
        description.appendText("a date string in format YYYY-MM-DD");
    }

    protected void describeMismatchSafely(Object item, Description description) {
        //description.appendText("was ").appendValue(item);
        description.appendText("was ").appendValue(item)
                .appendText(", which is not in format YYYY-MM-DD");
    }

    public static OrderDateMatcher orderDate() {
        return new OrderDateMatcher();
    }
}
