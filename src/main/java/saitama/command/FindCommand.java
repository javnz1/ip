package saitama.command;

import saitama.exception.SaitamaException;
import saitama.storage.Storage;
import saitama.task.Task;

import java.util.ArrayList;

public class FindCommand extends Command{
    private static final String HORIZONTAL_LINE = "____________________________________________________________\n";
    private String description;

    public FindCommand(String description) {
        this.description = description;
    }

    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) throws SaitamaException {
        StringBuilder output = new StringBuilder();

        if (description.isEmpty()) {
            throw new SaitamaException("ONE PUNCH!!! What are you looking for? 👊\n"
                    + "Please provide a keyword!\n"
                    + "find [keyword]");
        }

        output.append(HORIZONTAL_LINE);
        output.append("Here are the matching tasks in your list:\n");

        int findCount = 0;
        for (int i = 0; i < tasks.size(); i++) {
            Task current = tasks.get(i);
            if (current.description.toLowerCase().contains(description.toLowerCase())) {
                findCount++;
                output.append((i + 1)).append(".").append(tasks.get(i)).append("\n");
            }
        }

        if (findCount == 0) {
            output.append("No matching tasks found. Better luck next time! 👊\n");
        }
        output.append(HORIZONTAL_LINE);

        return output.toString();
    }
}
