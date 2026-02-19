package saitama.command;

import java.util.ArrayList;

import saitama.exception.SaitamaException;
import saitama.storage.Storage;
import saitama.task.Deadline;
import saitama.task.Task;

/**
 * Represents a command to add a deadline task to the task list.
 * This command parses the user input to extract the task description and the deadline date/time,
 * validates the input format, checks for duplicates, and saves the updated list to storage.
 */
public class DeadlineCommand extends Command {
    private static final String HORIZONTAL_LINE = "____________________________________________________________\n";
    private String description;
    private String by;

    /**
     * Constructs a new {@code DeadlineCommand} with the provided description.
     *
     * @param description The raw input containing the task details and the "/by" delimiter.
     */
    public DeadlineCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the deadline command.
     * Validates that the description is not empty, contains the required "/by" delimiter,
     * and follows the correct date-time format (dd-MM-yyyy HHmm).
     * It also prevents duplicate tasks from being added.
     *
     * @param tasks   The {@link ArrayList} of {@link Task} objects.
     * @param storage The {@link Storage} handler for saving data.
     * @return A success message confirming the addition of the deadline.
     * @throws SaitamaException If the input is malformed, the date format is invalid,
     *                          or a duplicate task is detected.
     */
    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) throws SaitamaException {
        StringBuilder output = new StringBuilder();

        if (description.isEmpty()) {
            throw new SaitamaException("ONE PUNCH!!! The PUNCH description can't be empty "
                    + "PLEASE describe it! 👊\n"
                    + "deadline [description] /by [dd-MM-yyyy HHmm]");
        } else if (!description.contains("/by")) {
            throw new SaitamaException("ONE PUNCH!!! Deadlines need a /by [dd-MM-yyyy HHmm] so that I "
                    + "know when I need to PUNCH before its gone! 👊\n"
                    + "deadline [description] /by [dd-MM-yyyy HHmm]");
        }

        String[] subcommand = description.split("/by ");
        this.description = subcommand[0].trim();

        if (subcommand.length < 2 || subcommand[1].trim().isEmpty()) {
            throw new SaitamaException("ONE PUNCH!!! The PUNCH /by can't be empty PLEASE "
                    + "input dd-MM-yyyy HHmm! 👊\n"
                    + "deadline [description] /by [dd-MM-yyyy HHmm]");
        }
        try {
            this.by = subcommand[1].trim();
            Task newTaskDeadline = new Deadline(description, by);

            for (Task existingTask : tasks) {
                if (existingTask.equals(newTaskDeadline)) {
                    throw new SaitamaException("ONE PUNCH!!! This task is already in your training list! 👊");
                }
            }

            tasks.add(newTaskDeadline);

            output.append(HORIZONTAL_LINE);
            output.append("Got it. I've added this task:\n");
            output.append(newTaskDeadline).append("\n");
            output.append("Now you have ").append(tasks.size()).append(" tasks in the list.\n");
            output.append(HORIZONTAL_LINE);

            storage.save(tasks);
        } catch (java.time.format.DateTimeParseException e) {
            throw new SaitamaException("ONE PUNCH!!! SaitamaSensei only understands dates in "
                    + "dd-MM-yyyy HHmm format! 👊\n"
                    + "deadline [description] /by [dd-MM-yyyy HHmm]");
        }
        return output.toString();
    }
}
