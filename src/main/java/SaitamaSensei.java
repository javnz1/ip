import java.util.ArrayList;
import java.util.Scanner;

public class SaitamaSensei {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> taskList = new ArrayList<>();

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
            } else {
                taskList.add(new Task(command));

                System.out.println(HORIZONTAL_LINE);
                System.out.println("Added: " + command);
                System.out.println(HORIZONTAL_LINE);
            }

            command = scanner.nextLine();
        }
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(HORIZONTAL_LINE);
    }
}
