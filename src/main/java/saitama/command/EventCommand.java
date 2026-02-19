package saitama.command;

import saitama.exception.SaitamaException;
import saitama.storage.Storage;
import saitama.task.Events;
import saitama.task.Task;

import java.util.ArrayList;

/**
 * Represents a command to add an event task to the task list.
 * This command parses the user input to extract the task description, start date,
 * and end date, validates the presence of required delimiters, checks for
 * duplicates, and persists the data to storage.
 */
public class EventCommand extends Command {
    private static final String HORIZONTAL_LINE = "____________________________________________________________\n";
    private String description;
    private String from;
    private String to;

    /**
     * Constructs an {@code EventCommand} with the provided raw input.
     *
     * @param description The full command string following the command word.
     */
    public EventCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the event addition logic.
     * Parses the input string for "/from" and "/to" tags, ensures date formats
     * are valid (dd-MM-yyyy), verifies the task is not a duplicate,
     * and updates the task list and storage.
     *
     * @param tasks   The {@link ArrayList} of {@link Task} objects.
     * @param storage The {@link Storage} handler for saving data.
     * @return A success message confirming the addition of the event.
     * @throws SaitamaException If the description is empty, delimiters are missing,
     *                          dates are malformed, or a duplicate is detected.
     */
    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) throws SaitamaException {
        StringBuilder output = new StringBuilder();

        if (description.isEmpty()) {
            throw new SaitamaException("ONE PUNCH!!! The PUNCH description can't be empty "
                    + "PLEASE describe it! 👊\n"
                    + "event [description] /from [dd-MM-yyyy] /to [dd-MM-yyyy]");
        }

        if (!description.contains("/from") || !description.contains("/to")) {
            throw new SaitamaException("ONE PUNCH!!! Events need both /from and /to times so that I know "
                    + "when I need to PUNCH before its gone! 👊\n"
                    + "event [description] /from [dd-MM-yyyy] /to [dd-MM-yyyy]");
        }

        String[] subcommandEvent = description.split("/from ");
        this.description = subcommandEvent[0].trim();
        String[] date = subcommandEvent[1].split("/to ");

        if (date.length < 2) {
            throw new SaitamaException("ONE PUNCH!!! The PUNCH /from and /to can't be empty "
                    + "PLEASE input dd-MM-yyyy for both! 👊\n"
                    + "event [description] /from [dd-MM-yyyy] /to [dd-MM-yyyy]");
        }
        this.from = date[0].trim();
        this.to = date[1].trim();
        if (from.isEmpty() || to.isEmpty()) {
            throw new SaitamaException("ONE PUNCH!!! The PUNCH /from and /to can't be empty "
                    + "PLEASE input dd-MM-yyyy for both! 👊\n"
                    + "event [description] /from [dd-MM-yyyy] /to [dd-MM-yyyy]");
        }
        java.time.LocalDate startDate = java.time.LocalDate.parse(from, java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        java.time.LocalDate endDate = java.time.LocalDate.parse(to, java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        if (startDate.isAfter(endDate)) {
            throw new SaitamaException("ONE PUNCH!!! Your start date (/from) cannot be after your end date (/to)! 👊\n"
                    + "Please ensure you PUNCH the dates in the right order!");
        }
        try {
            Task newTaskEvent = new Events(description, from, to);

            for (Task existingTask : tasks) {
                if (existingTask.equals(newTaskEvent)) {
                    throw new SaitamaException("ONE PUNCH!!! This task is already in your training list! 👊");
                }
            }

            tasks.add(newTaskEvent);

            output.append(HORIZONTAL_LINE);
            output.append("Got it. I've added this task:\n");
            output.append(newTaskEvent).append("\n");
            output.append("Now you have ").append(tasks.size()).append(" tasks in the list.\n");
            output.append(HORIZONTAL_LINE);

            storage.save(tasks);
        } catch (java.time.format.DateTimeParseException e) {
            throw new SaitamaException("ONE PUNCH!!! SaitamaSensei only understands dates "
                    + "in dd-MM-yyyy format! 👊\n"
                    + "event [description] /from [dd-MM-yyyy] /to [dd-MM-yyyy]");
        }
        return output.toString();
    }
}
