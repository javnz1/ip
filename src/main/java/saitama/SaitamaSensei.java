package saitama;

import saitama.command.Command;
import saitama.exception.SaitamaException;
import saitama.parser.Parser;
import saitama.storage.Storage;
import saitama.task.Task;

import java.util.ArrayList;

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
     * Generates a response for the user's chat message.
     */

    public String getResponse(String inputCommand) {
        try {
            Command c = Parser.parse(inputCommand);
            return c.execute(taskList, storage);
        } catch (SaitamaException e) {
            return HORIZONTAL_LINE + e.getMessage() + "\n" + HORIZONTAL_LINE;
        } catch (NumberFormatException e) {
            return HORIZONTAL_LINE + "ONE PUNCH!!! Please enter a valid number! 👊\n" + HORIZONTAL_LINE;
        } catch (IndexOutOfBoundsException e) {
            return HORIZONTAL_LINE + "ONE PUNCH!!! That task number doesn't exist in the list! 👊\n" + HORIZONTAL_LINE;
        } catch (Exception e) {
            return HORIZONTAL_LINE + "Something went wrong! 👊\n" + HORIZONTAL_LINE;
        }
    }
}
