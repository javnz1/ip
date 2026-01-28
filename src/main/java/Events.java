import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Events extends Task{
    protected LocalDate from;
    protected LocalDate to;

    public Events(String description, String from, String to) {
        super(description);
        this.from = LocalDate.parse(from.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));;
        this.to = LocalDate.parse(to.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));;
    }

    public String toString(){
        return "[E]" + super.toString() + " (from: " + from.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + " to: " + to.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }
}
