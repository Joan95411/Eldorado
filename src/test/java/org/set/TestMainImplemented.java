package org.set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMainImplemented {

    @Test
    public void testGetLucky() {
        assertEquals(7, Main.getLucky());
    }
}