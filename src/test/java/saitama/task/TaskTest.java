package saitama.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the {@link Task} hierarchy.
 * Validates string representations, equality logic for duplicate prevention,
 * and date parsing for specific task types.
 */
public class TaskTest {

    /**
     * Tests the string representation of a {@link ToDos} task.
     * Checks if the type icon [T] and status icon [ ] are correctly formatted.
     */
    @Test
    public void testTodoToString() {
        assertEquals("[T][ ] read book", new ToDos("read book").toString());
    }

    /**
     * Tests the equality logic between tasks.
     * Ensures that two tasks with the same description are considered equal,
     * regardless of case, which is essential for duplicate task prevention.
     */
    @Test
    public void testTaskEquality() {
        ToDos task1 = new ToDos("Training");
        ToDos task2 = new ToDos("training"); // test case insensitivity
        assertEquals(task1, task2);
    }

    /**
     * Tests the date parsing and formatting logic within a {@link Deadline} task.
     * Verifies that the internal LocalDateTime object correctly parses the input string
     * and can be formatted into a human-readable string.
     */
    @Test
    public void testDeadlineDateParsing() {
        Deadline d = new Deadline("Final Boss", "20-12-2024 1800");
        assertEquals("Dec 20 2024, 6:00pm", d.by.format(java.time.format.DateTimeFormatter.ofPattern("MMM d yyyy, " +
                "h:mma")));
    }
}