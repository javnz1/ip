package saitama.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task{
    public LocalDateTime by;

    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDateTime.parse(by.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
    }

    @Override
    public String toString(){
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM dd yyyy h.mma")) + ")";
    }
}
