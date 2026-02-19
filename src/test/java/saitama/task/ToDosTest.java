package saitama.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the {@link ToDos} task type.
 * Ensures that the string representation and core logic of a Todo task
 * function as expected.
 */
public class ToDosTest {

    /**
     * Verifies that the {@code toString()} method returns the correct
     * formatted string, including the type identifier [T] and the
     * initial completion status.
     */
    @Test
    public void testToString() {
        assertEquals("[T][ ] read book", new ToDos("read book").toString());
    }
}