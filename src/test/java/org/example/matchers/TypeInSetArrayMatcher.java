package org.example.matchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import net.javacrumbs.jsonunit.core.ParametrizedMatcher;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TypeInSetArrayMatcher extends TypeSafeMatcher<Object> implements ParametrizedMatcher {
    private final Set<String> allowedTypes = new HashSet<>();
    Map<?, ?> failedItem = null;

    // Receives "[A,B,C]" as parameter from golden JSON
    @Override
    public void setParameter(String parameter) {
        System.out.printf("[LOG][TypeInSetArrayMatcher] setParameter: '%s'%n", parameter);
        String cleaned = parameter.replaceAll("[\\[\\]\"]", "");
        //System.out.println("cleaned = " + cleaned);
        for (String type : cleaned.split(",")) {
            allowedTypes.add(type.trim());
        }
        System.out.printf("[LOG][TypeInSetArrayMatcher] Allowed types: %s%n", allowedTypes);
    }

    @Override
    protected boolean matchesSafely(Object actual) {
        System.out.printf("[LOG][TypeInSetArrayMatcher] matchesSafely, actual=%s%n", actual);
        List<?> deviceList;
        // json-unit supplies a List<LinkedHashMap> for arrays of objects
        if (actual instanceof List<?>) {
            deviceList = (List<?>) actual;
        } else {
            System.out.printf("[LOG][TypeInSetArrayMatcher] Not a list!%n");
            return false;
        }

        // Iterate over each device (expected to be a Map)
        for (int i = 0; i < deviceList.size(); i++) {
            Object item = deviceList.get(i);
            if (!(item instanceof Map)) {
                System.out.printf("[LOG][TypeInSetArrayMatcher] Element %d is not object/Map!%n", i);
                return false;
            }
            Map<?, ?> deviceObj = (Map<?, ?>) item;
            Object typeField = deviceObj.get("type");
            System.out.printf("[LOG][TypeInSetArrayMatcher] Element %d: type=%s%n", i, typeField);
            if (typeField == null || !allowedTypes.contains(typeField.toString())) {
                System.out.printf("[LOG][TypeInSetArrayMatcher] Element %d failed type check%n", i);
                System.out.printf("[LOG][TypeInSetArrayMatcher] Element that failed: %s%n", item);
                this.failedItem = (Map<?, ?>) item;
                return false;
            }
        }
        return true;
    }

    @Override
    public void describeTo(Description desc) {
        System.out.printf("[LOG][TypeInSetArrayMatcher] describeTo%n");
        desc.appendText("an array of device objects, each 'type' in " + allowedTypes);
    }

    @Override
    protected void describeMismatchSafely(Object actual, Description desc) {
        //desc.appendText("was ").appendValue(item);
        System.out.printf("[LOG][TypeInSetArrayMatcher] describeMismatchSafely, actual=%s%n", actual);

        if (this.failedItem != null) {
/*            desc.appendText(" the 'type' value does not match any value in ")
                    .appendText(allowedTypes.toString())
                    .appendText(" for item: ")
                    .appendText(this.failedItem.toString());*/
            // [IMP] Use ObjectMapper to convert the failed item to a JSON string otherwise it will be printed as a Map.
            // As map, it will get printed as {id=d2, type=X}, but your gherkin step expects it to be a JSON object like {"id":"d2","type":"X"}
            // Your expected string uses JSON formatting but the actual message uses Java Map formatting, hence use ObjectMapper to convert it to JSON and then check if the produced error message contains the expected error message.
            try {
                desc.appendText(" the 'type' value does not match any value in ")
                        .appendText(allowedTypes.toString())
                        .appendText(" for item: ")
                        .appendText(new ObjectMapper().writeValueAsString(this.failedItem));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            desc.appendText(" Input is invalid or does not match the expected structure.");
        }
    }
}
