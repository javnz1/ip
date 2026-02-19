package saitama.command;

import saitama.exception.SaitamaException;
import saitama.storage.Storage;
import saitama.task.Task;
import saitama.task.ToDos;

import java.util.ArrayList;

/**
 * Represents a command to add a todo task to the task list.
 * This command handles the creation of a simple task without any date or time constraints,
 * ensures the input is valid upon construction, and checks for duplicates during execution.
 */
public class ToDoCommand extends Command{
    private static final String HORIZONTAL_LINE = "____________________________________________________________\n";
    private String description;

    /**
     * Constructs a {@code ToDoCommand} with the specified description.
     * Validates immediately that the description is not empty to ensure
     * the command is in a valid state.
     *
     * @param description The task description provided by the user.
     * @throws SaitamaException If the provided description is empty.
     */
    public ToDoCommand(String description) throws SaitamaException {
        if (description.isEmpty()) {
            throw new SaitamaException("ONE PUNCH!!! The PUNCH description can't be empty "
                    + "PLEASE describe it! 👊\n"
                    + "todo [description]");
        }

        this.description = description;
    }

    /**
     * Executes the addition of a new todo task.
     * Verifies that the task does not already exist in the list before adding it
     * and persisting the updated list to storage.
     *
     * @param tasks The {@link ArrayList} of {@link Task} objects.
     * @param storage The {@link Storage} handler used to save the updated list.
     * @return A confirmation message showing the added task and the updated list count.
     * @throws SaitamaException If a duplicate task is detected in the list.
     */
    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) throws SaitamaException {
        StringBuilder output = new StringBuilder();

        Task newTask = new ToDos(description);

        for (Task existingTask : tasks) {
            if (existingTask.equals(newTask)) {
                throw new SaitamaException("ONE PUNCH!!! This task is already in your training list! 👊");
            }
        }

        tasks.add(newTask);
        output.append(HORIZONTAL_LINE);
        output.append("Got it. I've added this task:\n");
        output.append(newTask).append("\n");
        output.append("Now you have ").append(tasks.size()).append(" tasks in the list.\n");
        output.append(HORIZONTAL_LINE);
        storage.save(tasks);

        return output.toString();
    }
}
