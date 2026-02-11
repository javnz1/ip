package saitama;

import java.util.ArrayList;

import saitama.exception.SaitamaException;
import saitama.storage.Storage;
import saitama.task.Deadline;
import saitama.task.Events;
import saitama.task.Task;
import saitama.task.ToDos;

/**
 * Main entry point for the Saitama Sensei task management application.
 * Handles user input, command execution, and task list coordination.
 */
public class SaitamaSensei {
    private static Storage storage;
    private static final String HORIZONTAL_LINE = "____________________________________________________________\n";
    private static ArrayList<Task> taskList;

    /**
     * Construct a Saitama Sensei chat with the given file path
     *
     * @param filePath The file path of the task list in hard storage
     */
    public SaitamaSensei(String filePath) {
        storage = new Storage(filePath);
        taskList = storage.load();
        assert taskList != null : "taskList should be initialized after loading from Storage";
    }

    /**
     * Represents the various commands supported by Saitama Sensei.
     */
    public enum CommandType {
        TODO, DEADLINE, EVENT, LIST, MARK, UNMARK, DELETE, FIND, BYE, UNKNOWN
    }

    /**
     * Prints the confirmation message when a task is successfully added to the list.
     *
     * @param task The task that was added.
     */
    public static String taskString(Task task) {
        StringBuilder outputTask = new StringBuilder();
        outputTask.append(HORIZONTAL_LINE);
        outputTask.append("Got it. I've added this task:\n");
        outputTask.append(task);
        outputTask.append("Now you have ").append(taskList.size()).append(" tasks in the list.\n");
        outputTask.append(HORIZONTAL_LINE);

        return outputTask.toString();
    }

    /**
     * Parses the user input to determine which command to execute.
     *
     * @param input The full line of text entered by the user.
     * @return The corresponding CommandType enum, or UNKNOWN if the input is invalid.
     */
    private static CommandType getCommandType(String input) {
        assert input != null : "input command should not be null";

        try {
            String firstWord = input.split(" ")[0].toUpperCase();
            return CommandType.valueOf(firstWord);
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            return CommandType.UNKNOWN;
        }
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String command) {
        CommandType type = getCommandType(command);
        StringBuilder output = new StringBuilder();

        try {
            switch (type) {
            case LIST:
                output.append(HORIZONTAL_LINE);
                output.append("Here are the tasks in your list:\n");
                for (int i = 0; i < taskList.size(); i++) {
                    output.append((i + 1)).append(".").append(taskList.get(i)).append("\n");
                }
                output.append(HORIZONTAL_LINE);
                break;
            case UNMARK:
                command = command.replace("unmark", "").trim();
                if (command.isEmpty()) {
                    throw new SaitamaException("ONE PUNCH!!! Please let Sensei know which Task Number you "
                            + "like to UN-PUNCH! 👊\n"
                            + "if you do not know the task number please type 'list' to view list then do\n"
                            + "umark [task number in the list]");
                }

                int numUnmark = Integer.parseInt(command) - 1;
                taskList.get(numUnmark).unmarkAsDone();
                output.append(HORIZONTAL_LINE);
                output.append("OK, I've marked this task as not done yet:\n");
                output.append(taskList.get(numUnmark)).append("\n");
                output.append(HORIZONTAL_LINE);
                storage.save(taskList);
                break;
            case MARK:
                command = command.replace("mark", "").trim();
                if (command.isEmpty()) {
                    throw new SaitamaException("ONE PUNCH!!! Please let Sensei know which Task Number "
                            + "you like to PUNCH! 👊\n"
                            + "if you do not know the task number please type 'list' to view list then do\n"
                            + "mark [task number in the list]");
                }

                int numMark = Integer.parseInt(command) - 1;
                taskList.get(numMark).markAsDone();
                output.append(HORIZONTAL_LINE);
                output.append("Nice! I've marked this task as done:\n");
                output.append(taskList.get(numMark));
                output.append(HORIZONTAL_LINE);
                storage.save(taskList);
                break;
            case TODO:
                String description = command.replace("todo", "").trim();
                if (description.isEmpty()) {
                    throw new SaitamaException("ONE PUNCH!!! The PUNCH description can't be empty "
                            + "PLEASE describe it! 👊\n"
                            + "todo [description]");
                }

                Task newTask = new ToDos(description);
                taskList.add(newTask);
                output.append(taskString(newTask));
                storage.save(taskList);
                break;
            case DEADLINE:
                if (!command.contains("/by")) {
                    throw new SaitamaException("ONE PUNCH!!! Deadlines need a /by [yyyy-mm-dd HHmm] so that I "
                            + "know when I need to PUNCH before its gone! 👊\n"
                            + "deadline [description] /by [yyyy-MM-dd HHmm]");
                }

                String[] subcommand = command.split("/by ");
                String descriptionDeadline = subcommand[0].replace("deadline ", "").trim();
                if (descriptionDeadline.isEmpty()) {
                    throw new SaitamaException("ONE PUNCH!!! The PUNCH description can't be empty "
                            + "PLEASE describe it! 👊\n"
                            + "deadline [description] /by [yyyy-MM-dd HHmm]");
                } else if (subcommand.length < 2 || subcommand[1].trim().isEmpty()) {
                    throw new SaitamaException("ONE PUNCH!!! The PUNCH /by can't be empty PLEASE "
                            + "input yyyy-MM-dd HHmm! 👊\n"
                            + "deadline [description] /by [yyyy-MM-dd HHmm]");
                }
                try {
                    String by = subcommand[1].trim();

                    Task newTaskDeadline = new Deadline(descriptionDeadline, by);
                    taskList.add(newTaskDeadline);
                    output.append(taskString(newTaskDeadline));
                    storage.save(taskList);
                } catch (java.time.format.DateTimeParseException e) {
                    throw new SaitamaException("ONE PUNCH!!! SaitamaSensei only understands dates in "
                            + "yyyy-MM-dd HHmm format! 👊\n"
                            + "deadline [description] /by [yyyy-MM-dd HHmm]");
                }
                break;
            case EVENT:
                if (!command.contains("/from") || !command.contains("/to")) {
                    throw new SaitamaException("ONE PUNCH!!! Events need both /from and /to times so that I know "
                            + "when I need to PUNCH before its gone! 👊\n"
                            + "event [description] /from [yyyy-MM-dd] /to [yyyy-MM-dd]");
                }

                String[] subcommandEvent = command.split("/from ");
                String descriptionEvent = subcommandEvent[0].replace("event ", "").trim();
                if (descriptionEvent.isEmpty()) {
                    throw new SaitamaException("ONE PUNCH!!! The PUNCH description can't be empty "
                            + "PLEASE describe it! 👊\n"
                            + "event [description] /from [yyyy-MM-dd] /to [yyyy-MM-dd]");
                }

                String[] date = subcommandEvent[1].split("/to ");
                if (date.length < 2) {
                    throw new SaitamaException("ONE PUNCH!!! The PUNCH /from and /to can't be empty "
                            + "PLEASE input yyyy-MM-dd for both! 👊\n"
                            + "event [description] /from [yyyy-MM-dd] /to [yyyy-MM-dd]");
                }
                String from = date[0].trim();
                String to = date[1].trim();
                if (from.isEmpty() || to.isEmpty()) {
                    throw new SaitamaException("ONE PUNCH!!! The PUNCH /from and /to can't be empty "
                            + "PLEASE input yyyy-MM-dd for both! 👊\n"
                            + "event [description] /from [yyyy-MM-dd] /to [yyyy-MM-dd]");
                }

                try {
                    Task newTaskEvent = new Events(descriptionEvent, from, to);
                    taskList.add(newTaskEvent);
                    output.append(taskString(newTaskEvent));
                    storage.save(taskList);
                } catch (java.time.format.DateTimeParseException e) {
                    throw new SaitamaException("ONE PUNCH!!! SaitamaSensei only understands dates "
                            + "in yyyy-MM-dd format! 👊\n"
                            + "event [description] /from [yyyy-MM-dd] /to [yyyy-MM-dd]");
                }
                break;
            case DELETE:
                command = command.replace("delete", "").trim();
                if (command.isEmpty()) {
                    throw new SaitamaException("ONE PUNCH!!! Which task are we deleting? 👊\n"
                            + "if you do not know the task number please type 'list' to view list then do\n"
                            + "delete [task number in the list]");
                }

                int numDelete = Integer.parseInt(command) - 1;
                Task removedTask = taskList.remove(numDelete);

                output.append(HORIZONTAL_LINE);
                output.append("Noted. I've removed this task:\n");
                output.append(removedTask).append("\n");
                output.append("Now you have ").append(taskList.size()).append(" tasks in the list.\n");
                output.append(HORIZONTAL_LINE);
                storage.save(taskList);
                break;
            case FIND:
                command = command.replace("find", "").trim();
                if (command.isEmpty()) {
                    throw new SaitamaException("ONE PUNCH!!! What are you looking for? 👊\n"
                            + "Please provide a keyword!\n"
                            + "find [keyword]");
                }

                output.append(HORIZONTAL_LINE);
                output.append("Here are the matching tasks in your list:");

                int findCount = 0;
                for (int i = 0; i < taskList.size(); i++) {
                    Task current = taskList.get(i);
                    if (current.description.toLowerCase().contains(command.toLowerCase())) {
                        findCount++;
                        output.append((i + 1)).append(".").append(taskList.get(i)).append("\n");
                    }
                }

                if (findCount == 0) {
                    output.append("No matching tasks found. Better luck next time! 👊\n");
                }
                output.append(HORIZONTAL_LINE);
                break;
            case BYE:
                output.append(HORIZONTAL_LINE);
                output.append("Bye. Hope to see you again soon!");
                output.append(HORIZONTAL_LINE);
                break;
            case UNKNOWN:
            default:
                throw new SaitamaException("ONE PUNCH!!! I don't understand you. "
                        + "Please input a specific PUNCH command in the format below:\n"
                        + "todo [description]\n"
                        + "deadline [description] /by [date/time/day]\n"
                        + "event [description] /from [date/time/day] /to [date/time/day]\n"
                        + "list\n"
                        + "mark [task number in the list]\n"
                        + "unmark [task number in the list]\n"
                        + "find [task keyword]\n"
                        + "delete [task number in the list]");
            }
        } catch (SaitamaException e) {
            output.append(HORIZONTAL_LINE);
            output.append(e.getMessage()).append("\n");
            output.append(HORIZONTAL_LINE);
        } catch (NumberFormatException e) {
            output.append(HORIZONTAL_LINE);
            output.append("ONE PUNCH!!! Please enter a valid number! 👊\n");
            output.append(HORIZONTAL_LINE);
        } catch (IndexOutOfBoundsException e) {
            output.append(HORIZONTAL_LINE);
            output.append("ONE PUNCH!!! That task number doesn't exist in the list! 👊\n");
            output.append(HORIZONTAL_LINE);
        }

        return output.toString();
    }
}
