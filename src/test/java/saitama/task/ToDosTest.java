package saitama.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDosTest {
    @Test
    public void testToString() {
        assertEquals("[T][ ] read book", new ToDos("read book").toString());
    }
}