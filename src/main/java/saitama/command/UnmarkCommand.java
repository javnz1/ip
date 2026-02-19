package saitama.command;

import saitama.exception.SaitamaException;
import saitama.storage.Storage;
import saitama.task.Task;

import java.util.ArrayList;

/**
 * Represents a command to revert a specific task's status to not completed.
 * This command identifies a task by its index, updates its status to "not done",
 * and synchronizes the change with the storage file.
 */
public class UnmarkCommand extends Command{
    private static final String HORIZONTAL_LINE = "____________________________________________________________\n";
    private String description;

    /**
     * Constructs an {@code UnmarkCommand} with the target task index.
     *
     * @param description The task number string (1-based index) provided by the user.
     */
    public UnmarkCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the unmarking of a task.
     * Converts the user-provided index to a 0-based integer, updates the
     * task's internal state to incomplete, and saves the list to storage.
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
            throw new SaitamaException("ONE PUNCH!!! Please let Sensei know which Task Number you "
                    + "like to UN-PUNCH! 👊\n"
                    + "if you do not know the task number please type 'list' to view list then do\n"
                    + "umark [task number in the list]");
        }
        int unmarkIdx = Integer.parseInt(description) - 1;

        tasks.get(unmarkIdx).unmarkAsDone();
        output.append(HORIZONTAL_LINE);
        output.append("OK, I've marked this task as not done yet:\n");
        output.append(tasks.get(unmarkIdx)).append("\n");
        output.append(HORIZONTAL_LINE);
        storage.save(tasks);

        return output.toString();
    }
}
