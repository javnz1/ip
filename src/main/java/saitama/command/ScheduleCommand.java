package saitama.command;

import saitama.exception.SaitamaException;
import saitama.storage.Storage;
import saitama.task.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ScheduleCommand extends Command{
    private static final String HORIZONTAL_LINE = "____________________________________________________________\n";
    private String description;

    public ScheduleCommand(String description) {
        this.description = description;
    }

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

            int count = 0;
            for (Task task : tasks) {
                if (task.isOnDate(checkDate)) {
                    count++;
                    output.append(count).append(".").append(task).append("\n");
                }
            }

            if (count == 0) {
                output.append("No tasks found on specific date (").append(checkDate).append("). Better luck next time! 👊\n");
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
