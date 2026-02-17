package saitama.parser;

import saitama.command.ByeCommand;
import saitama.command.Command;
import saitama.command.DeadlineCommand;
import saitama.command.DeleteCommand;
import saitama.command.EventCommand;
import saitama.command.FindCommand;
import saitama.command.ListCommand;
import saitama.command.MarkCommand;
import saitama.command.ScheduleCommand;
import saitama.command.ToDoCommand;
import saitama.command.UnmarkCommand;
import saitama.exception.SaitamaException;

public class Parser {
    /**
     * Represents the various commands supported by Saitama Sensei.
     */
    public enum CommandType {
        TODO, DEADLINE, EVENT, LIST, MARK, UNMARK, DELETE, FIND, SCHEDULE, BYE, UNKNOWN
    }


    public static Command parse(String input) throws SaitamaException {
        assert input != null : "input command should not be null";

        CommandType type = getCommandType(input);
        String inputCommand = input;

        String firstWord = input.split(" ")[0];
        String action = inputCommand.replace(firstWord, "").trim();

        switch (type) {
        case LIST:
            return new ListCommand();
        case UNMARK:
            return new UnmarkCommand(action);
        case MARK:
            return new MarkCommand(action);
        case TODO:
            return new ToDoCommand(action);
        case DEADLINE:
            return new DeadlineCommand(action);
        case EVENT:
            return new EventCommand(action);
        case DELETE:
            return new DeleteCommand(action);
        case FIND:
            return new FindCommand(action);
        case SCHEDULE:
            return new ScheduleCommand(action);
        case BYE:
            return new ByeCommand();
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
                    + "schedule [date]\n"
                    + "delete [task number in the list]");
        }
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
}
