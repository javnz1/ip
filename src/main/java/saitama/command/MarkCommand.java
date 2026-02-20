package saitama.command;

import java.util.ArrayList;

import saitama.exception.SaitamaException;
import saitama.storage.Storage;
import saitama.task.Task;

/**
 * Represents a command to mark a specific task as completed.
 * This command identifies a task by its index in the task list, updates its
 * status to "done", and ensures the change is reflected in the storage file.
 */
public class MarkCommand extends Command {
    private static final String HORIZONTAL_LINE = "_______________________________\n";
    private String description;

    /**
     * Constructs a {@code MarkCommand} with the target task index.
     *
     * @param description The task number string (1-based index) provided by the user.
     */
    public MarkCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the marking of a task as done.
     * Converts the user-provided index to a 0-based integer, updates the
     * task's status, and saves the modified task list to storage.
     *
     * @param tasks The {@link ArrayList} of {@link Task} objects.
     * @param storage The {@link Storage} handler used to persist the state change.
     * @return A confirmation message showing the updated task status.
     * @throws SaitamaException If the description is empty.
     * @throws NumberFormatException If the description is not a valid integer.
     * @throws IndexOutOfBoundsException If the task number is outside the range of the list.
     */
    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) throws SaitamaException {
        StringBuilder output = new StringBuilder();

        if (description.isEmpty()) {
            throw new SaitamaException("ONE PUNCH!!! Please let Sensei know which Task Number "
                    + "you like to PUNCH! 👊\n"
                    + "if you do not know the task number please type 'list' to view list then do\n"
                    + "mark [task number in the list]");
        }
        int markIdx = Integer.parseInt(description) - 1;

        tasks.get(markIdx).markAsDone();
        output.append(HORIZONTAL_LINE);
        output.append("Nice! I've marked this task as done:\n");
        output.append(tasks.get(markIdx)).append("\n");
        output.append(HORIZONTAL_LINE);
        storage.save(tasks);

        return output.toString();
    }
}
