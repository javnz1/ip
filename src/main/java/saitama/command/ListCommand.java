package saitama.command;

import saitama.exception.SaitamaException;
import saitama.storage.Storage;
import saitama.task.Task;

import java.util.ArrayList;

public class ListCommand extends Command{
    private static final String HORIZONTAL_LINE = "____________________________________________________________\n";

    public ListCommand() {
    }

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
