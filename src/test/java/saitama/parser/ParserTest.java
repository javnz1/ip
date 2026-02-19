package saitama.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import saitama.command.ToDoCommand;
import saitama.exception.SaitamaException;

/**
 * Test class for the {@link Parser}.
 * Validates that user input strings are correctly converted into {@code Command} objects
 * and ensures that malformed inputs trigger the appropriate {@link SaitamaException}.
 */
public class ParserTest {

    /**
     * Verifies that a valid "todo" command string is correctly parsed into
     * a {@link ToDoCommand} instance.
     */
    @Test
    public void parse_validTodo_success() throws SaitamaException {
        assertTrue(Parser.parse("todo borrow book") instanceof ToDoCommand);
    }

    /**
     * Tests the defensive coding logic for empty descriptions.
     * Ensures that providing a "todo" command without a description throws a
     * {@link SaitamaException} with the specific "ONE PUNCH" error message.
     */
    @Test
    public void parse_emptyTodoDescription_exceptionThrown() {
        try {
            Parser.parse("todo   ");
            fail(); // the test should not reach this line
        } catch (SaitamaException e) {
            assertEquals("ONE PUNCH!!! The PUNCH description can't be empty "
                    + "PLEASE describe it! 👊\n"
                    + "todo [description]", e.getMessage());
        }
    }

    /**
     * Verifies that unrecognized command words trigger an exception.
     * Ensures the user is notified with a help message when an invalid
     * command keyword is entered.
     */
    @Test
    public void parse_invalidCommand_exceptionThrown() {
        try {
            Parser.parse("superman fly");
            fail();
        } catch (SaitamaException e) {
            assertTrue(e.getMessage().contains("ONE PUNCH!!! I don't understand you. "
                    + "Please input a specific PUNCH command in the format below:\n"
                    + "list\n"
                    + "todo [description]\n"
                    + "deadline [description] /by [dd-MM-yyyy HHmm]n"
                    + "event [description] /from [dd-MM-yyyy] /to [dd-MM-yyyy]\n"
                    + "mark [task number in the list]\n"
                    + "unmark [task number in the list]\n"
                    + "find [task keyword]\n"
                    + "schedule [dd-MM-yyyy]\n"
                    + "delete [task number in the list]"));
        }
    }
}
