package saitama.command;

import java.util.ArrayList;

import saitama.exception.SaitamaException;
import saitama.storage.Storage;
import saitama.task.Task;

/**
 * Represents a command to search for tasks within the task list.
 * This command filters tasks based on whether their descriptions contain
 * a specific keyword provided by the user.
 */
public class FindCommand extends Command {
    private static final String HORIZONTAL_LINE = "____________________________________________________________\n";
    private String description;

    /**
     * Constructs a {@code FindCommand} with the specified search keyword.
     *
     * @param description The keyword used to match against task descriptions.
     */
    public FindCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the find command.
     * Iterates through the task list and identifies tasks that contain the keyword.
     * The search is case-insensitive to ensure broad matching.
     *
     * @param tasks   The {@link ArrayList} of {@link Task} objects to search through.
     * @param storage The {@link Storage} handler (unused by this command).
     * @return A formatted list of matching tasks or a "no match" message.
     * @throws SaitamaException If the search keyword is empty.
     */
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

        // Create a list of strings representing the matching tasks
        java.util.List<String> foundTasks = java.util.stream.IntStream.range(0, tasks.size())
                .filter(i -> tasks.get(i).description.toLowerCase()
                        .contains(description.toLowerCase()))
                .mapToObj(i -> (i + 1) + "." + tasks.get(i))
                .collect(java.util.stream.Collectors.toList());

        // Terminal logic to build the output
        if (foundTasks.isEmpty()) {
            output.append("No matching tasks found. Better luck next time! 👊\n");
        } else {
            foundTasks.forEach(taskStr -> output.append(taskStr).append("\n"));
        }
        output.append(HORIZONTAL_LINE);

        return output.toString();
    }
}
