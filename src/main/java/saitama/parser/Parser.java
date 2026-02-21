package saitama.parser;

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

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

/**
 * Deals with making sense of the user command.
 * This class translates raw user input strings into executable {@link Command} objects.
 */
public class Parser {
    public static final DateTimeFormatter DATE_INPUT_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-uuuu").withResolverStyle(ResolverStyle.STRICT);

    /**
     * Represents the various commands supported by Saitama Sensei.
     * Used internally to map raw strings to logical command categories.
     */
    public enum CommandType {
        TODO, DEADLINE, EVENT, LIST, MARK, UNMARK, DELETE, FIND, SCHEDULE, BYE, UNKNOWN
    }

    /**
     * Parses the full user input and returns the appropriate {@link Command} object.
     * Uses a switch-case logic based on the {@link CommandType} to determine which
     * command subclass to instantiate.
     *
     * @param input The full line of text entered by the user.
     * @return A {@link Command} object ready for execution.
     * @throws SaitamaException If the command word is unrecognized or the input is malformed.
     */
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
        case UNKNOWN:
        default:
            throw new SaitamaException("ONE PUNCH!!! I don't understand you. "
                    + "Please input a specific PUNCH command in the format below:\n"
                    + "👊 list\n"
                    + "👊 todo [description]\n"
                    + "👊 deadline [description] /by [dd-MM-yyyy HHmm]\n"
                    + "👊 event [description] /from [dd-MM-yyyy] /to [dd-MM-yyyy]\n"
                    + "👊 mark [task number in the list]\n"
                    + "👊 unmark [task number in the list]\n"
                    + "👊 find [task keyword]\n"
                    + "👊 schedule [dd-MM-yyyy]\n"
                    + "👊 delete [task number in the list]\n"
                    + "👊 bye");
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
