import java.util.Scanner;

public class SaitamaSensei {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(HORIZONTAL_LINE);
        System.out.println("Hello! I'm Saitama Sensei!");
        System.out.println("What can I do for you?");
        System.out.println(HORIZONTAL_LINE);

        String command = scanner.nextLine();

        while (!command.equalsIgnoreCase("bye")) {
            System.out.println(HORIZONTAL_LINE);
            System.out.println(command);
            System.out.println(HORIZONTAL_LINE);

            command = scanner.nextLine();
        }
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(HORIZONTAL_LINE);
    }
}
