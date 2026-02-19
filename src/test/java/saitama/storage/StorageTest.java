package saitama.storage;

import org.junit.jupiter.api.Test;
import saitama.task.Task;
import saitama.task.ToDos;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link Storage} component.
 * Verifies that the data conversion logic between the saved file format
 * and the application's task objects remains consistent and accurate.
 */
public class StorageTest {

    /**
     * Tests the conversion from a stored file string to a {@link ToDos} object.
     * Verifies that the task type, completion status (where '1' represents done),
     * and description are correctly reconstructed.
     */
    @Test
    public void testLineToTask_toDo_parsedCorrectly() {
        Storage storage = new Storage("./data/test.txt");
        Task result = storage.lineToTask("T | 1 | join sports club");

        assertInstanceOf(ToDos.class, result);
        assertEquals("[T][X] join sports club", result.toString());
    }
}
