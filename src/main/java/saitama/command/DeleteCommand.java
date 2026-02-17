package saitama.command;

import saitama.exception.SaitamaException;
import saitama.storage.Storage;
import saitama.task.Task;

import java.util.ArrayList;

public class DeleteCommand extends Command{
    private static final String HORIZONTAL_LINE = "____________________________________________________________\n";
    private String description;

    public DeleteCommand(String description) {
        this.description = description;
    }

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
