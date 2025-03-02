import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TodoList todoList = new TodoList();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            TodoList.printMenu();
            System.out.print("Ваш выбор: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    todoList.addTask(scanner);
                    break;
                case 2:
                    todoList.printTasks();
                    break;
                case 3:
                    todoList.deleteTaskByNumber(scanner);
                    break;
                case 4:
                    todoList.deleteTaskByName(scanner);
                    break;
                case 5:
                    todoList.deleteTasksByKeyword(scanner);
                    break;
                case 0:
                    System.out.println("Выход из программы.");
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        } while (choice != 0);

        scanner.close();
    }

}