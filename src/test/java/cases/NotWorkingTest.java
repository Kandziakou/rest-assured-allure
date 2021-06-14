package cases;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("This testcase is just example of broken, failed and disabled tests")
@Epic("Trash")
public class NotWorkingTest {
    @Disabled
    @Test
    @Severity(SeverityLevel.TRIVIAL)
    @DisplayName("This test need here just to show how look disabled test")
    @Description("Bla-bla-bla")
    void disabledTest(){
        Assertions.assertTrue(1>2);
    }

    @Test
    @Severity(SeverityLevel.TRIVIAL)
    @DisplayName("This test need here just to show how look failed test")
    @Description("Bla-bla-bla")
    void failedTest(){
        Assertions.assertTrue(1>2);
    }

    @Test
    @Severity(SeverityLevel.TRIVIAL)
    @DisplayName("This test need here just to show how look broken test")
    @Description("Bla-bla-bla")
    void BrokenTest(){
        int number = 1/0;
        Assertions.assertTrue(number > 0);
    }
}
