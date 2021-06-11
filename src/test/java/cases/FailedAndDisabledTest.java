package cases;

import io.qameta.allure.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("This testcase is just example of failed and disabled tests")
public class FailedAndDisabledTest {
    @Test
    @Disabled
    @DisplayName("This test need here just to show how look disabled test")
    @Description("Bla-bla-bla")
    void disabledTest(){
        Assertions.assertTrue(1>2);
    }

    @Test
    @DisplayName("This test need here just to show how look failed test")
    @Description("Bla-bla-bla")
    void failedTest(){
        Assertions.assertTrue(1>2);
    }
}
