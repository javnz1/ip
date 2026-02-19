package saitama.command;

import org.junit.jupiter.api.Test;
import saitama.exception.SaitamaException;
import saitama.task.Task;
import saitama.task.ToDos;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for {@link ToDoCommand}.
 * Focuses on verifying the execution logic of the command, particularly
 * the enforcement of business rules such as duplicate task prevention.
 */
public class ToDoCommandTest {

    /**
     * Tests that a {@link SaitamaException} is thrown when attempting to add a
     * task that is already present in the task list.
     * This test ensures that the {@code execute} method correctly identifies
     * duplicates based on the task description.
     * * @throws SaitamaException if the command creation fails during setup.
     */
    @Test
    public void execute_duplicateTask_exceptionThrown() throws SaitamaException {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDos("100 Pushups"));

        ToDoCommand command = new ToDoCommand("100 Pushups");

        assertThrows(SaitamaException.class, () -> {
            command.execute(tasks, null); // Storage can be null for this unit test
        });
    }
}