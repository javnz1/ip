import java.util.ArrayList;
import java.util.Scanner;

public class SaitamaSensei {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private static ArrayList<Task> taskList;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        taskList = new ArrayList<>();

        System.out.println(HORIZONTAL_LINE);
        System.out.println("Hello! I'm Saitama Sensei!");
        System.out.println("What can I do for you?");
        System.out.println(HORIZONTAL_LINE);

        String command = scanner.nextLine();

        while (!command.equalsIgnoreCase("bye")) {
            if (command.equalsIgnoreCase("list")){
                System.out.println(HORIZONTAL_LINE);
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskList.size(); i++){
                    System.out.println((i+1) + "." + taskList.get(i));
                }
                System.out.println(HORIZONTAL_LINE);
            } else if (command.startsWith("mark")){
                int num = Integer.parseInt(command.split(" ")[1]) - 1;
                System.out.println(HORIZONTAL_LINE);
                System.out.println("Nice! I've marked this task as done:");
                taskList.get(num).markAsDone();
                System.out.println(taskList.get(num));
                System.out.println(HORIZONTAL_LINE);
            } else if (command.startsWith("unmark")){
                int num = Integer.parseInt(command.split(" ")[1]) - 1;
                System.out.println(HORIZONTAL_LINE);
                System.out.println("OK, I've marked this task as not done yet:");
                taskList.get(num).unmarkAsDone();
                System.out.println(taskList.get(num));
                System.out.println(HORIZONTAL_LINE);
            } else if (command.startsWith("todo")) {
                String description = command.replace("todo ", "").trim();

                Task new_task = new ToDos(description);
                taskList.add(new_task);
                taskString(new_task);
            } else if (command.startsWith("deadline")) {
                String[] subcommand = command.split("/by ");
                String description = subcommand[0].replace("deadline ", "").trim();
                String by = subcommand[1];

                Task new_task = new Deadline(description,by);
                taskList.add(new_task);
                taskString(new_task);
            } else if (command.startsWith("event")) {
                String[] subcommand = command.split("/from ");
                String description = subcommand[0].replace("event ", "").trim();

                String[] date = subcommand[1].split("/to ");
                String from = date[0].trim();
                String to = date[1].trim();

                Task new_task = new Events(description,from, to);
                taskList.add(new_task);
                taskString(new_task);
            }

            command = scanner.nextLine();
        }
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(HORIZONTAL_LINE);
    }

    public static void taskString(Task task){
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + taskList.size() + " tasks in the list.");
        System.out.println(HORIZONTAL_LINE);
    }
}
