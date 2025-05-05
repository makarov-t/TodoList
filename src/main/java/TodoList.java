import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class TodoList {

    static void printMenu() {
        System.out.println("Выберите операцию:");
        System.out.println("0. Выход из программы");
        System.out.println("1. Добавить дело");
        System.out.println("2. Показать дела");
        System.out.println("3. Удалить дело по номеру");
        System.out.println("4. Удалить дело по названию");
        System.out.println("5. Удалить дела по ключевому слову");
    }

    private final ArrayList<String> tasks;

    public TodoList() {
        tasks = new ArrayList<>();
    }

    public void addTask(Scanner scanner) {
        System.out.print("Введите название задачи: ");
        String task = scanner.nextLine();
        if (tasks.contains(task)) {
            System.out.println("Такое дело уже существует.");
        } else {
            tasks.add(task);
            System.out.println("Добавлено!");
        }
        printTasks();
    }

    public void printTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Ваш список дел пуст.");
        } else {
            System.out.println("Ваш список дел:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    public void deleteTaskByNumber(Scanner scanner) {
        System.out.print("Введите номер для удаления: ");
        int number = scanner.nextInt();
        scanner.nextLine();

        if (number >= 1 && number <= tasks.size()) {
            tasks.remove(number - 1);
            System.out.println("Удалено!");
        } else {
            System.out.println("Нет дела с таким номером.");
        }
        printTasks();
    }

    public void deleteTaskByName(Scanner scanner) {
        System.out.print("Введите задачу для удаления: ");
        String task = scanner.nextLine();

        if (tasks.contains(task)) {
            tasks.remove(task);
            System.out.println("Удалено!");
        } else {
            System.out.println("Нет дела с таким названием.");
        }
        printTasks();
    }

    public void deleteTasksByKeyword(Scanner scanner) {
        System.out.print("Введите ключевое слово для удаления: ");
        String keyword = scanner.nextLine();

        Iterator<String> iterator = tasks.iterator();
        boolean removed = false;

        while (iterator.hasNext()) {
            String task = iterator.next();
            if (task.contains(keyword)) {
                iterator.remove();
                removed = true;
            }
        }

        if (removed) {
            System.out.println("Удалены все дела, содержащие ключевое слово.");
        } else {
            System.out.println("Нет дел, содержащих ключевое слово.");
        }
        printTasks();
    }
}
