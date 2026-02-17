package saitama.command;

import saitama.exception.SaitamaException;
import saitama.storage.Storage;
import saitama.task.Events;
import saitama.task.Task;

import java.util.ArrayList;

public class EventCommand extends Command{
    private static final String HORIZONTAL_LINE = "____________________________________________________________\n";
    private String description;
    private String from;
    private String to;

    public EventCommand (String description) {
        this.description = description;
    }

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

        try {
            Task newTaskEvent = new Events(description, from, to);
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
