package saitama;

import saitama.exception.SaitamaException;
import saitama.storage.Storage;
import saitama.task.Deadline;
import saitama.task.Events;
import saitama.task.Task;
import saitama.task.ToDos;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main entry point for the Saitama Sensei task management application.
 * Handles user input, command execution, and task list coordination.
 */
public class SaitamaSensei {
    private static Storage storage;
    private static final String HORIZONTAL_LINE = "____________________________________________________________\n";
    private static ArrayList<Task> taskList;

    public SaitamaSensei (String filePath) {
        storage = new Storage(filePath);
        taskList = storage.load();
    }

    /**
     * Represents the various commands supported by Saitama Sensei.
     */
    public enum CommandType {
        TODO, DEADLINE, EVENT, LIST, MARK, UNMARK, DELETE, FIND, BYE, UNKNOWN
    }

    /**
     * Initializes the application, loads tasks from storage, and enters the command loop.
     *
     * @param args Command line arguments (not used).
     */
    /*
    public static void main(String[] args) {
        taskList = storage.load();

        Scanner scanner = new Scanner(System.in);

        System.out.println(HORIZONTAL_LINE);
        System.out.println("Hello! I'm Saitama Sensei!");
        System.out.println("What can I do for you?");
        System.out.println(HORIZONTAL_LINE);

        while (true) {
            String command = scanner.nextLine();
            CommandType type = getCommandType(command);

            if (type == CommandType.BYE) {
                break; // Exit the loop gracefully
            }

            try {
                switch (type) {
                case LIST:
                    System.out.println(HORIZONTAL_LINE);
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < taskList.size(); i++) {
                        System.out.println((i + 1) + "." + taskList.get(i));
                    }
                    System.out.println(HORIZONTAL_LINE);
                    break;
                case UNMARK:
                    command = command.replace("unmark", "").trim();
                    if (command.isEmpty())
                        throw new SaitamaException("ONE PUNCH!!! Please let Sensei know which Task Number you like to UN-PUNCH! 👊\n" +
                                "if you do not know the task number please type 'list' to view list then do\n" +
                                "umark [task number in the list]");

                    int num_unmark = Integer.parseInt(command) - 1;
                    taskList.get(num_unmark).unmarkAsDone();
                    System.out.println(HORIZONTAL_LINE);
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(taskList.get(num_unmark));
                    System.out.println(HORIZONTAL_LINE);
                    storage.save(taskList);
                    break;
                case MARK:
                    command = command.replace("mark", "").trim();
                    if (command.isEmpty())
                        throw new SaitamaException("ONE PUNCH!!! Please let Sensei know which Task Number you like to PUNCH! 👊\n" +
                                "if you do not know the task number please type 'list' to view list then do\n" +
                                "mark [task number in the list]");

                    int num_mark = Integer.parseInt(command) - 1;
                    taskList.get(num_mark).markAsDone();
                    System.out.println(HORIZONTAL_LINE);
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(taskList.get(num_mark));
                    System.out.println(HORIZONTAL_LINE);
                    storage.save(taskList);
                    break;
                case TODO:
                    String description = command.replace("todo", "").trim();
                    if (description.isEmpty())
                        throw new SaitamaException("ONE PUNCH!!! The PUNCH description can't be empty PLEASE describe it! 👊\n" +
                                "todo [description]");

                    Task new_task = new ToDos(description);
                    taskList.add(new_task);
                    taskString(new_task);
                    storage.save(taskList);
                    break;
                case DEADLINE:
                    if (!command.contains("/by"))
                        throw new SaitamaException("ONE PUNCH!!! Deadlines need a /by [yyyy-mm-dd HHmm] so that I know when I need to PUNCH before its gone! 👊\n" +
                                "deadline [description] /by [yyyy-MM-dd HHmm]");

                    String[] subcommand = command.split("/by ");
                    String description_deadline = subcommand[0].replace("deadline ", "").trim();
                    if (description_deadline.isEmpty())
                        throw new SaitamaException("ONE PUNCH!!! The PUNCH description can't be empty PLEASE describe it! 👊\n" +
                                "deadline [description] /by [yyyy-MM-dd HHmm]");
                    else if (subcommand.length < 2 || subcommand[1].trim().isEmpty())
                        throw new SaitamaException("ONE PUNCH!!! The PUNCH /by can't be empty PLEASE input yyyy-MM-dd HHmm! 👊\n" +
                                "deadline [description] /by [yyyy-MM-dd HHmm]");
                    try {
                        String by = subcommand[1].trim();

                        Task new_task_deadline = new Deadline(description_deadline, by);
                        taskList.add(new_task_deadline);
                        taskString(new_task_deadline);
                        storage.save(taskList);
                    } catch (java.time.format.DateTimeParseException e) {
                        throw new SaitamaException("ONE PUNCH!!! SaitamaSensei only understands dates in yyyy-MM-dd HHmm format! 👊\n" +
                                "deadline [description] /by [yyyy-MM-dd HHmm]");
                    }
                    break;
                case EVENT:
                    if (!command.contains("/from") || !command.contains("/to"))
                        throw new SaitamaException("ONE PUNCH!!! Events need both /from and /to times so that I know when I need to PUNCH before its gone! 👊\n" +
                                "event [description] /from [yyyy-MM-dd] /to [yyyy-MM-dd]");

                    String[] subcommand_event = command.split("/from ");
                    String description_event = subcommand_event[0].replace("event ", "").trim();
                    if (description_event.isEmpty())
                        throw new SaitamaException("ONE PUNCH!!! The PUNCH description can't be empty PLEASE describe it! 👊\n" +
                                "event [description] /from [yyyy-MM-dd] /to [yyyy-MM-dd]");

                    String[] date = subcommand_event[1].split("/to ");
                    if (date.length < 2)
                        throw new SaitamaException("ONE PUNCH!!! The PUNCH /from and /to can't be empty PLEASE input yyyy-MM-dd for both! 👊\n" +
                                "event [description] /from [yyyy-MM-dd] /to [yyyy-MM-dd]");
                    String from = date[0].trim();
                    String to = date[1].trim();
                    if (from.isEmpty() || to.isEmpty())
                        throw new SaitamaException("ONE PUNCH!!! The PUNCH /from and /to can't be empty PLEASE input yyyy-MM-dd for both! 👊\n" +
                                "event [description] /from [yyyy-MM-dd] /to [yyyy-MM-dd]");

                    try {
                        Task new_task_event = new Events(description_event, from, to);
                        taskList.add(new_task_event);
                        taskString(new_task_event);
                        storage.save(taskList);
                    } catch (java.time.format.DateTimeParseException e) {
                        throw new SaitamaException("ONE PUNCH!!! SaitamaSensei only understands dates in yyyy-MM-dd format! 👊\n" +
                                "event [description] /from [yyyy-MM-dd] /to [yyyy-MM-dd]");
                    }
                    break;
                case DELETE:
                    command = command.replace("delete", "").trim();
                    if (command.isEmpty()) {
                        throw new SaitamaException("ONE PUNCH!!! Which task are we deleting? 👊\n" +
                                "if you do not know the task number please type 'list' to view list then do\n" +
                                "delete [task number in the list]");
                    }

                    int num_delete = Integer.parseInt(command) - 1;
                    Task removedTask = taskList.remove(num_delete);

                    System.out.println(HORIZONTAL_LINE);
                    System.out.println("Noted. I've removed this task:");
                    System.out.println(removedTask);
                    System.out.println("Now you have " + taskList.size() + " tasks in the list.");
                    System.out.println(HORIZONTAL_LINE);
                    storage.save(taskList);
                    break;
                case FIND:
                    command = command.replace("find", "").trim();
                    if (command.isEmpty()) {
                        throw new SaitamaException("ONE PUNCH!!! What are you looking for? 👊\n" +
                                "Please provide a keyword!\n" +
                                "find [keyword]");
                    }

                    System.out.println(HORIZONTAL_LINE);
                    System.out.println("Here are the matching tasks in your list:");

                    int findCount = 0;
                    for (int i = 0; i < taskList.size(); i++) {
                        Task current = taskList.get(i);
                        if (current.description.toLowerCase().contains(command.toLowerCase())) {
                            findCount++;
                            System.out.println((i + 1) + "." + taskList.get(i));
                        }
                    }

                    if (findCount == 0) {
                        System.out.println("No matching tasks found. Better luck next time! 👊");
                    }
                    System.out.println(HORIZONTAL_LINE);
                    break;
                case UNKNOWN:
                default:
                    throw new SaitamaException("ONE PUNCH!!! I don't understand you. Please input a specific PUNCH command in the format below:\n" +
                            "todo [description]\n" +
                            "deadline [description] /by [date/time/day]\n" +
                            "event [description] /from [date/time/day] /to [date/time/day]\n" +
                            "list\n" +
                            "mark [task number in the list]\n" +
                            "unmark [task number in the list]\n" +
                            "find [task keyword]\n" +
                            "delete [task number in the list]");
                }
            } catch (SaitamaException e) {
                System.out.println(HORIZONTAL_LINE);
                System.out.println(e.getMessage());
                System.out.println(HORIZONTAL_LINE);
            } catch (NumberFormatException e) {
                System.out.println(HORIZONTAL_LINE);
                System.out.println("ONE PUNCH!!! Please enter a valid number! 👊");
                System.out.println(HORIZONTAL_LINE);
            } catch (IndexOutOfBoundsException e) {
                System.out.println(HORIZONTAL_LINE);
                System.out.println("ONE PUNCH!!! That task number doesn't exist in the list! 👊");
                System.out.println(HORIZONTAL_LINE);
            }
        }
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(HORIZONTAL_LINE);
    }
     */

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
                if (command.isEmpty())
                    throw new SaitamaException("ONE PUNCH!!! Please let Sensei know which Task Number you like to UN-PUNCH! 👊\n" +
                            "if you do not know the task number please type 'list' to view list then do\n" +
                            "umark [task number in the list]");

                int num_unmark = Integer.parseInt(command) - 1;
                taskList.get(num_unmark).unmarkAsDone();
                output.append(HORIZONTAL_LINE);
                output.append("OK, I've marked this task as not done yet:\n");
                output.append(taskList.get(num_unmark)).append("\n");
                output.append(HORIZONTAL_LINE);
                storage.save(taskList);
                break;
            case MARK:
                command = command.replace("mark", "").trim();
                if (command.isEmpty())
                    throw new SaitamaException("ONE PUNCH!!! Please let Sensei know which Task Number you like to PUNCH! 👊\n" +
                            "if you do not know the task number please type 'list' to view list then do\n" +
                            "mark [task number in the list]");

                int num_mark = Integer.parseInt(command) - 1;
                taskList.get(num_mark).markAsDone();
                output.append(HORIZONTAL_LINE);
                output.append("Nice! I've marked this task as done:\n");
                output.append(taskList.get(num_mark));
                output.append(HORIZONTAL_LINE);
                storage.save(taskList);
                break;
            case TODO:
                String description = command.replace("todo", "").trim();
                if (description.isEmpty())
                    throw new SaitamaException("ONE PUNCH!!! The PUNCH description can't be empty PLEASE describe it! 👊\n" +
                            "todo [description]");

                Task new_task = new ToDos(description);
                taskList.add(new_task);
                output.append(taskString(new_task));
                storage.save(taskList);
                break;
            case DEADLINE:
                if (!command.contains("/by"))
                    throw new SaitamaException("ONE PUNCH!!! Deadlines need a /by [yyyy-mm-dd HHmm] so that I know when I need to PUNCH before its gone! 👊\n" +
                            "deadline [description] /by [yyyy-MM-dd HHmm]");

                String[] subcommand = command.split("/by ");
                String description_deadline = subcommand[0].replace("deadline ", "").trim();
                if (description_deadline.isEmpty())
                    throw new SaitamaException("ONE PUNCH!!! The PUNCH description can't be empty PLEASE describe it! 👊\n" +
                            "deadline [description] /by [yyyy-MM-dd HHmm]");
                else if (subcommand.length < 2 || subcommand[1].trim().isEmpty())
                    throw new SaitamaException("ONE PUNCH!!! The PUNCH /by can't be empty PLEASE input yyyy-MM-dd HHmm! 👊\n" +
                            "deadline [description] /by [yyyy-MM-dd HHmm]");
                try {
                    String by = subcommand[1].trim();

                    Task new_task_deadline = new Deadline(description_deadline, by);
                    taskList.add(new_task_deadline);
                    output.append(taskString(new_task_deadline));
                    storage.save(taskList);
                } catch (java.time.format.DateTimeParseException e) {
                    throw new SaitamaException("ONE PUNCH!!! SaitamaSensei only understands dates in yyyy-MM-dd HHmm format! 👊\n" +
                            "deadline [description] /by [yyyy-MM-dd HHmm]");
                }
                break;
            case EVENT:
                if (!command.contains("/from") || !command.contains("/to"))
                    throw new SaitamaException("ONE PUNCH!!! Events need both /from and /to times so that I know when I need to PUNCH before its gone! 👊\n" +
                            "event [description] /from [yyyy-MM-dd] /to [yyyy-MM-dd]");

                String[] subcommand_event = command.split("/from ");
                String description_event = subcommand_event[0].replace("event ", "").trim();
                if (description_event.isEmpty())
                    throw new SaitamaException("ONE PUNCH!!! The PUNCH description can't be empty PLEASE describe it! 👊\n" +
                            "event [description] /from [yyyy-MM-dd] /to [yyyy-MM-dd]");

                String[] date = subcommand_event[1].split("/to ");
                if (date.length < 2)
                    throw new SaitamaException("ONE PUNCH!!! The PUNCH /from and /to can't be empty PLEASE input yyyy-MM-dd for both! 👊\n" +
                            "event [description] /from [yyyy-MM-dd] /to [yyyy-MM-dd]");
                String from = date[0].trim();
                String to = date[1].trim();
                if (from.isEmpty() || to.isEmpty())
                    throw new SaitamaException("ONE PUNCH!!! The PUNCH /from and /to can't be empty PLEASE input yyyy-MM-dd for both! 👊\n" +
                            "event [description] /from [yyyy-MM-dd] /to [yyyy-MM-dd]");

                try {
                    Task new_task_event = new Events(description_event, from, to);
                    taskList.add(new_task_event);
                    output.append(taskString(new_task_event));
                    storage.save(taskList);
                } catch (java.time.format.DateTimeParseException e) {
                    throw new SaitamaException("ONE PUNCH!!! SaitamaSensei only understands dates in yyyy-MM-dd format! 👊\n" +
                            "event [description] /from [yyyy-MM-dd] /to [yyyy-MM-dd]");
                }
                break;
            case DELETE:
                command = command.replace("delete", "").trim();
                if (command.isEmpty()) {
                    throw new SaitamaException("ONE PUNCH!!! Which task are we deleting? 👊\n" +
                            "if you do not know the task number please type 'list' to view list then do\n" +
                            "delete [task number in the list]");
                }

                int num_delete = Integer.parseInt(command) - 1;
                Task removedTask = taskList.remove(num_delete);

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
                    throw new SaitamaException("ONE PUNCH!!! What are you looking for? 👊\n" +
                            "Please provide a keyword!\n" +
                            "find [keyword]");
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
                throw new SaitamaException("ONE PUNCH!!! I don't understand you. Please input a specific PUNCH command in the format below:\n" +
                        "todo [description]\n" +
                        "deadline [description] /by [date/time/day]\n" +
                        "event [description] /from [date/time/day] /to [date/time/day]\n" +
                        "list\n" +
                        "mark [task number in the list]\n" +
                        "unmark [task number in the list]\n" +
                        "find [task keyword]\n" +
                        "delete [task number in the list]");
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
