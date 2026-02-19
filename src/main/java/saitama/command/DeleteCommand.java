package saitama.command;

import java.util.ArrayList;

import saitama.exception.SaitamaException;
import saitama.storage.Storage;
import saitama.task.Task;

/**
 * Represents a command to remove a specific task from the task list.
 * This command parses the task index provided by the user, removes the
 * corresponding task from the list, and updates the hard disk storage.
 */
public class DeleteCommand extends Command {
    private static final String HORIZONTAL_LINE = "____________________________________________________________\n";
    private String description;

    /**
     * Constructs a {@code DeleteCommand} with the target task index.
     *
     * @param description The task number string (1-based index) provided by the user.
     */
    public DeleteCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the deletion of a task from the list.
     * Converts the user-provided index to a 0-based integer, removes the task,
     * and saves the resulting task list to storage.
     *
     * @param tasks The {@link ArrayList} of {@link Task} objects.
     * @param storage The {@link Storage} handler used to save the updated list.
     * @return A confirmation message showing the removed task and the new list size.
     * @throws SaitamaException If the description is empty.
     */
    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) throws SaitamaException {
        StringBuilder output = new StringBuilder();

        if (description.isEmpty()) {
            throw new SaitamaException("ONE PUNCH!!! Which task are we deleting? 👊\n"
                    + "if you do not know the task number please type 'list' to view list then do\n"
                    + "delete [task number in the list]");
        }

        int numDelete = Integer.parseInt(description) - 1;

        Task removedTask = tasks.remove(numDelete);

        output.append(HORIZONTAL_LINE);
        output.append("Noted. I've removed this task:\n");
        output.append(removedTask).append("\n");
        output.append("Now you have ").append(tasks.size()).append(" tasks in the list.\n");
        output.append(HORIZONTAL_LINE);
        storage.save(tasks);

        return output.toString();
    }
}
