package saitama;

import java.util.ArrayList;

import saitama.command.Command;
import saitama.exception.SaitamaException;
import saitama.parser.Parser;
import saitama.storage.Storage;
import saitama.task.Task;

/**
 * saitama.Main entry point and coordinator for the Saitama Sensei task management application.
 * This class orchestrates the interaction between the user interface, the command parser,
 * the task list, and the persistent storage system.
 */
public class SaitamaSensei {
    private static Storage storage;
    private static final String HORIZONTAL_LINE = "_______________________________\n";
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
     * Processes user input and generates a response.
     * This method acts as the primary interface for the UI, translating user strings
     * into commands and capturing any execution errors to present as user-friendly feedback.
     *
     * @param inputCommand The raw string input from the user.
     * @return A {@code String} response from Saitama Sensei regarding the action performed.
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
            return HORIZONTAL_LINE + "Something went wrong! Please ensure you input the right format and valid "
                    + "dates 👊\n" + HORIZONTAL_LINE;
        }
    }

    /**
     * Returns a welcome message and a list of available commands.
     * This greeting is displayed when the application starts to guide the user.
     *
     * @return A string containing the welcome message and command format instructions.
     */
    public String getGreeting() {
        return "Welcome to SaitamaSensei Taskbot. I'm Saitama. I'm a hero for fun.\n"
                + "What's on your schedule today? Here's how to PUNCH in commands:\n\n"
                + "👊 list\n"
                + "👊 todo [description]\n"
                + "👊 deadline [description] /by [dd-MM-yyyy HHmm]\n"
                + "👊 event [description] /from [dd-MM-yyyy] /to [dd-MM-yyyy]\n"
                + "👊 mark [task number in the list]\n"
                + "👊 unmark [task number in the list]\n"
                + "👊 find [task keyword]\n"
                + "👊 schedule [dd-MM-yyyy]\n"
                + "👊 delete [task number in the list]\n"
                + "👊 bye";
    }
}
