package saitama.command;

import saitama.exception.SaitamaException;
import saitama.storage.Storage;
import saitama.task.Deadline;
import saitama.task.Task;

import java.time.LocalDate;
import java.util.ArrayList;

public class DeadlineCommand extends Command{
    private static final String HORIZONTAL_LINE = "____________________________________________________________\n";
    private String description;
    private String by;

    public DeadlineCommand(String description) {
        this.description = description;
    }

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

        if (subcommand.length < 2 || subcommand[1].trim().isEmpty()) {
            throw new SaitamaException("ONE PUNCH!!! The PUNCH /by can't be empty PLEASE "
                    + "input dd-MM-yyyy HHmm! 👊\n"
                    + "deadline [description] /by [dd-MM-yyyy HHmm]");
        }
        try {
            this.by = subcommand[1].trim();
            Task newTaskDeadline = new Deadline(description, by);
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
