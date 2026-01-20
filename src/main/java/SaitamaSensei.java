import java.util.ArrayList;
import java.util.Scanner;

public class SaitamaSensei {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> taskList = new ArrayList<>();

        System.out.println(HORIZONTAL_LINE);
        System.out.println("Hello! I'm Saitama Sensei!");
        System.out.println("What can I do for you?");
        System.out.println(HORIZONTAL_LINE);

        String command = scanner.nextLine();

        while (!command.equalsIgnoreCase("bye")) {
            if (!command.equalsIgnoreCase("list")){
                taskList.add(command);

                System.out.println(HORIZONTAL_LINE);
                System.out.println("Added: " + command);
                System.out.println(HORIZONTAL_LINE);
            } else {
                System.out.println(HORIZONTAL_LINE);
                for (int i = 0; i < taskList.size(); i++){
                    System.out.println((i+1) + ". " + taskList.get(i));
                }
                System.out.println(HORIZONTAL_LINE);
            }

            command = scanner.nextLine();
        }
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(HORIZONTAL_LINE);
    }
}
