import org.example.matchers.PrefixMatcher;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

public class UuidMatcherStepsUnitTest {
    @Test
    void should_match_string_with_prefix() {
        assertThat("USR-12345", PrefixMatcher.prefix("USR-"));
    }

    @Test
    void should_not_match_string_without_prefix() {
        assertThat("ZZZ-000", not(PrefixMatcher.prefix("USR-")));
    }

    @Test
    void should_describe_mismatch() {
        PrefixMatcher matcher = PrefixMatcher.prefix("USR-");
        StringBuilder sb = new StringBuilder();
        org.hamcrest.StringDescription desc = new org.hamcrest.StringDescription();
        matcher.describeMismatch("NOPE-999", desc);
        System.out.println("[LOG] Mismatch description: " + desc.toString());
        // Output will help you debug readable errors in CI runs
    }
}