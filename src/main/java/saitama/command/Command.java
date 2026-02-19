package saitama.command;

import java.util.ArrayList;

import saitama.exception.SaitamaException;
import saitama.storage.Storage;
import saitama.task.Task;

/**
 * Represents an executable command in the Saitama Sensei application.
 * This abstract class serves as the base for all specific command types,
 * defining the core contract for execution and application termination logic.
 */
public abstract class Command {

    /**
     * Executes the command logic, performing operations on the provided task list
     * and storage handler.
     *
     * @param tasks The {@link ArrayList} of {@link Task} objects to be manipulated.
     * @param storage The {@link Storage} object used to persist changes to the hard disk.
     * @return A {@code String} representing Saitama's response to the user's action.
     * @throws SaitamaException If an error occurs during the execution of the command.
     */
    public abstract String execute(ArrayList<Task> tasks, Storage storage) throws SaitamaException;

}
