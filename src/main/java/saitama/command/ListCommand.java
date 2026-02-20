package saitama.command;

import java.util.ArrayList;

import saitama.exception.SaitamaException;
import saitama.storage.Storage;
import saitama.task.Task;

/**
 * Represents a command to display all tasks currently in the task list.
 * This command iterates through the list and formats each task with its
 * corresponding index for the user to view.
 */
public class ListCommand extends Command {
    private static final String HORIZONTAL_LINE = "_______________________________\n";

    /**
     * Constructs a new {@code ListCommand} instance.
     */
    public ListCommand() {
    }

    /**
     * Executes the list command.
     * Generates a numbered string representation of all tasks in the list.
     *
     * @param tasks The {@link ArrayList} of {@link Task} objects to be displayed.
     * @param storage The {@link Storage} handler (unused by this command).
     * @return A formatted string listing all tasks, framed by horizontal lines.
     * @throws SaitamaException If an error occurs during the execution of the command.
     */
    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) throws SaitamaException {
        StringBuilder output = new StringBuilder();

        output.append(HORIZONTAL_LINE);
        output.append("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            output.append((i + 1)).append(".").append(tasks.get(i)).append("\n");
        }
        output.append(HORIZONTAL_LINE);
        return output.toString();
    }
}
