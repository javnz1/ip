package saitama.command;

import saitama.exception.SaitamaException;
import saitama.storage.Storage;
import saitama.task.Task;

import java.util.ArrayList;

public class ByeCommand extends Command{
    private static final String HORIZONTAL_LINE = "____________________________________________________________\n";

    public ByeCommand() {
    }

    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) throws SaitamaException {
        StringBuilder output = new StringBuilder();

        output.append(HORIZONTAL_LINE);
        output.append("Bye. Hope to see you again soon!");
        output.append(HORIZONTAL_LINE);

        return output.toString();
    }
}
