package saitama.exception;

/**
 * Represents specialized exceptions that occur within the Saitama Sensei application.
 * Used for handling user input errors and logic violations with custom "One Punch" messages.
 */
public class SaitamaException extends Exception{
    /**
     * Constructs a new SaitamaException with the specified error message.
     *
     * @param message The detailed error message describing what went wrong.
     */
    public SaitamaException(String message){
        super(message);
    }
}
