package saitama.command;

import saitama.exception.SaitamaException;
import saitama.storage.Storage;
import saitama.task.Task;

import java.util.ArrayList;

public class MarkCommand extends Command{
    private static final String HORIZONTAL_LINE = "____________________________________________________________\n";
    private String description;

    public MarkCommand(String description) {
        this.description = description;
    }

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
        output.append(tasks.get(markIdx));
        output.append(HORIZONTAL_LINE);
        storage.save(tasks);

        return output.toString();
    }
}
