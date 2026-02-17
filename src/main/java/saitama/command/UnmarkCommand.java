package saitama.command;

import saitama.exception.SaitamaException;
import saitama.storage.Storage;
import saitama.task.Task;

import java.util.ArrayList;

public class UnmarkCommand extends Command{
    private static final String HORIZONTAL_LINE = "____________________________________________________________\n";
    private String description;

    public UnmarkCommand(String description) {
        this.description = description;
    }

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
