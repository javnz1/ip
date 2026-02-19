package saitama.command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import saitama.exception.SaitamaException;
import saitama.storage.Storage;
import saitama.task.Task;

/**
 * Represents a command to view tasks occurring on a specific date.
 * This command parses a user-provided date string and filters the task list
 * for any Deadlines or Events that match that particular date.
 */
public class ScheduleCommand extends Command {
    private static final String HORIZONTAL_LINE = "____________________________________________________________\n";
    private String description;

    /**
     * Constructs a {@code ScheduleCommand} with the specified date string.
     *
     * @param description The date to search for, in dd-MM-yyyy format.
     */
    public ScheduleCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the schedule search logic.
     * Parses the input into a {@link LocalDate}, iterates through the task list,
     * and identifies tasks occurring on that date using the {@code isOnDate} method.
     *
     * @param tasks   The {@link ArrayList} of {@link Task} objects to filter.
     * @param storage The {@link Storage} handler (unused by this command).
     * @return A formatted list of tasks occurring on the specified date.
     * @throws SaitamaException If the input is empty or the date format is invalid.
     */
    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) throws SaitamaException {
        StringBuilder output = new StringBuilder();

        if (description.isEmpty()) {
            throw new SaitamaException("ONE PUNCH!!! What task are you looking for a specific date? 👊\n"
                    + "If you do not know the date please type 'list' to view list then do \n"
                    + "schedule [dd-MM-yyyy]");
        }

        try {
            LocalDate checkDate = LocalDate.parse(description, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            output.append(HORIZONTAL_LINE);
            output.append("Here are the tasks in your list on the specific date (")
                    .append(checkDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy")))
                    .append("):\n");

            // Filter the tasks and map them to their formatted strings
            java.util.List<String> foundTasks = java.util.stream.IntStream.range(0, tasks.size())
                    .filter(i -> tasks.get(i).isOnDate(checkDate))
                    .mapToObj(i -> (i + 1) + "." + tasks.get(i))
                    .collect(java.util.stream.Collectors.toList());

            if (foundTasks.isEmpty()) {
                output.append("No tasks found on specific date (")
                        .append(checkDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy")))
                        .append("). Better luck next time! 👊\n");
            } else {
                foundTasks.forEach(taskString -> output.append(taskString).append("\n"));
            }
            output.append(HORIZONTAL_LINE);
        } catch (java.time.format.DateTimeParseException e) {
            throw new SaitamaException("ONE PUNCH!!! SaitamaSensei only understands dates "
                    + "in dd-MM-yyyy format! 👊\n"
                    + "schedule [dd-MM-yyyy]");
        }

        return output.toString();
    }
}
