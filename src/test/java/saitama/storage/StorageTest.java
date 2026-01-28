package saitama.storage;

import org.junit.jupiter.api.Test;
import saitama.task.Task;
import saitama.task.ToDos;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {
    @Test
    public void testLineToTask_toDo_parsedCorrectly() {
        Storage storage = new Storage("./data/test.txt");
        Task result = storage.lineToTask("T | 1 | join sports club");

        assertInstanceOf(ToDos.class, result);
        assertEquals("[T][X] join sports club", result.toString());
    }
}
