package saitama.command;

import java.util.ArrayList;

import saitama.exception.SaitamaException;
import saitama.storage.Storage;
import saitama.task.Task;

/**
 * Represents a command to terminate the Saitama Sensei application.
 * This command generates a farewell message for the user and signals the end of the session.
 */
public class ByeCommand extends Command {
    private static final String HORIZONTAL_LINE = "____________________________________________________________\n";

    /**
     * Constructs a new {@code ByeCommand} instance.
     */
    public ByeCommand() {
    }

    /**
     * Executes the exit command.
     * Returns a farewell message to be displayed to the user before the application closes.
     *
     * @param tasks The list of tasks (unused by this command).
     * @param storage The storage handler (unused by this command).
     * @return A string containing the exit message.
     * @throws SaitamaException If an error occurs during execution.
     */
    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) throws SaitamaException {
        StringBuilder output = new StringBuilder();

        output.append(HORIZONTAL_LINE);
        output.append("Bye. Hope to see you again soon!").append("\n");
        output.append(HORIZONTAL_LINE);

        return output.toString();
    }
}
