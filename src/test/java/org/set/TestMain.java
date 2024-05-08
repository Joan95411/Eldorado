package org.set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMain {

    @Test
    public void testImplemented() {
        assertEquals(7, Main.getLucky());
    }

    @Test
    public void testNotImplemented() {
        assertEquals(10, Main.getLucky());
    }
}