package saitama.command;

import saitama.exception.SaitamaException;
import saitama.storage.Storage;
import saitama.task.Task;
import saitama.task.ToDos;

import java.util.ArrayList;

public class ToDoCommand extends Command{
    private static final String HORIZONTAL_LINE = "____________________________________________________________\n";
    private String description;

    public ToDoCommand(String description) {
        this.description = description;
    }

    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) throws SaitamaException {
        StringBuilder output = new StringBuilder();

        if (description.isEmpty()) {
            throw new SaitamaException("ONE PUNCH!!! The PUNCH description can't be empty "
                    + "PLEASE describe it! 👊\n"
                    + "todo [description]");
        }

        Task newTask = new ToDos(description);
        tasks.add(newTask);
        output.append(HORIZONTAL_LINE);
        output.append("Got it. I've added this task:\n");
        output.append(newTask).append("\n");
        output.append("Now you have ").append(tasks.size()).append(" tasks in the list.\n");
        output.append(HORIZONTAL_LINE);
        storage.save(tasks);

        return output.toString();
    }
}
