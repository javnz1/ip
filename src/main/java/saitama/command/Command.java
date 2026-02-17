package saitama.command;

import java.util.ArrayList;

import saitama.exception.SaitamaException;
import saitama.storage.Storage;
import saitama.task.Task;

public abstract class Command {
    /** Executes the command and returns the response string */
    public abstract String execute(ArrayList<Task> tasks, Storage storage) throws SaitamaException;

    /** Indicates if this command should terminate the app */
    public boolean isExit() {
        return false;
    }
}
