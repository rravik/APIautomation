package utils;

import org.testng.asserts.SoftAssert;

public class SoftAssertionUtil {

    private static SoftAssert softAssertInstance;

    private SoftAssertionUtil() {

    }

    public static SoftAssert getInstance(){
        if(softAssertInstance == null) {
            softAssertInstance = new SoftAssert();
        }
        return softAssertInstance;

    }

    public void assertTrue(boolean condition, String message) {

        try {
            getInstance().assertTrue(condition, message);

        } catch (AssertionError e) {
            getInstance().fail(message);
        }

    }

    public void assertFalse(boolean condition, String message) {

        try {
            getInstance().assertFalse(condition, message);
        } catch (AssertionError e) {
            getInstance().fail(message);
        }

    }

    public void assertEquals(Object actual, Object expected, String message) {

        try{
            getInstance().assertEquals(actual, expected, message);
        } catch (AssertionError e) {
            getInstance().fail(message);
        }
    }

    public void assertNotEquals(Object actual, Object expected, String message) {

        try{
            getInstance().assertNotEquals(actual, expected, message);
        } catch (AssertionError e) {
            getInstance().fail(message);
        }
    }


    public void assertAll() {
        getInstance().assertAll();
    }
}
