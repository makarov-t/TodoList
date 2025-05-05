import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class TodoListTest {

    private TodoList todoList;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        todoList = new TodoList();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void whenAddUniqueTask_thenTaskAdded() {
        String input = "Новая задача";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        todoList.addTask(scanner);

        assertThat(todoList, hasProperty("tasks", hasSize(1)));
        assertThat(todoList, hasProperty("tasks", hasItem("Новая задача")));
        assertThat(outputStream.toString(), containsString("Добавлено!"));
    }

    @Test
    void whenAddDuplicateTask_thenTaskNotAdded() {
        String input = "Задача\nЗадача";
        Scanner scanner1 = new Scanner(new ByteArrayInputStream("Задача".getBytes()));
        Scanner scanner2 = new Scanner(new ByteArrayInputStream("Задача".getBytes()));

        todoList.addTask(scanner1);
        todoList.addTask(scanner2);

        assertThat(todoList, hasProperty("tasks", hasSize(1)));
        assertThat(outputStream.toString(), containsString("Такое дело уже существует."));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Задача 1", "Важная задача", "Покупки"})
    void whenDeleteTaskByName_thenTaskRemoved(String taskName) {
        todoList.addTask(new Scanner(new ByteArrayInputStream(taskName.getBytes())));

        todoList.deleteTaskByName(new Scanner(new ByteArrayInputStream(taskName.getBytes())));

        assertThat(todoList, hasProperty("tasks", not(hasItem(taskName))));
        assertThat(outputStream.toString(), containsString("Удалено!"));
    }

    @ParameterizedTest
    @CsvSource({
            "1, Задача один",
            "2, Задача два",
            "3, Задача три"
    })
    void whenDeleteTaskByNumber_thenCorrectTaskRemoved(int taskNumber, String expectedTask) {
        todoList.addTask(new Scanner(new ByteArrayInputStream("Задача один".getBytes())));
        todoList.addTask(new Scanner(new ByteArrayInputStream("Задача два".getBytes())));
        todoList.addTask(new Scanner(new ByteArrayInputStream("Задача три".getBytes())));

        todoList.deleteTaskByNumber(new Scanner(new ByteArrayInputStream(String.valueOf(taskNumber).getBytes())));

        assertThat(todoList, hasProperty("tasks", not(hasItem(expectedTask))));
        assertThat(todoList, hasProperty("tasks", hasSize(2)));
    }

    @Test
    void whenDeleteTasksByKeyword_thenAllMatchingTasksRemoved() {
        todoList.addTask(new Scanner(new ByteArrayInputStream("Купить молоко".getBytes())));
        todoList.addTask(new Scanner(new ByteArrayInputStream("Купить хлеб".getBytes())));
        todoList.addTask(new Scanner(new ByteArrayInputStream("Позвонить отцу".getBytes())));

        todoList.deleteTasksByKeyword(new Scanner(new ByteArrayInputStream("Купить".getBytes())));

        assertThat(todoList, hasProperty("tasks", not(hasItem(containsString("Купить")))));
        assertThat(todoList, hasProperty("tasks", hasSize(1)));
        assertThat(todoList, hasProperty("tasks", hasItem("Позвонить отцу")));
        assertThat(outputStream.toString(), containsString("Удалены все дела, содержащие ключевое слово."));
    }

    @Test
    void whenPrintEmptyList_thenCorrectMessageShown() {
        todoList.printTasks();

        assertThat(outputStream.toString(), containsString("Ваш список дел пуст."));
    }
}