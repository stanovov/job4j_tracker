package ru.job4j.tracker;

import ru.job4j.tracker.action.*;
import ru.job4j.tracker.input.ConsoleInput;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.input.ValidateInput;
import ru.job4j.tracker.output.ConsoleOutput;
import ru.job4j.tracker.output.Output;
import ru.job4j.tracker.store.SqlTracker;
import ru.job4j.tracker.store.Store;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class StartUI {

    private final Output out;

    public StartUI(Output output) {
        this.out = output;
    }

    private static String loadSysEnvIfNullThenConfig(String sysEnv, String key, Properties config) {
        String value = System.getenv(sysEnv);
        if (value == null) {
            value = config.getProperty(key);
        }
        return value;
    }

    private static Connection loadConnection() throws ClassNotFoundException, SQLException {
        var config = new Properties();
        try (InputStream in = StartUI.class.getClassLoader()
                .getResourceAsStream("app.properties")) {
            config.load(in);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        String url = loadSysEnvIfNullThenConfig("JDBC_URL", "url", config);
        String username = loadSysEnvIfNullThenConfig("JDBC_USERNAME", "username", config);
        String password = loadSysEnvIfNullThenConfig("JDBC_PASSWORD", "password", config);
        String driver = loadSysEnvIfNullThenConfig("JDBC_DRIVER", "driver-class-name", config);
        System.out.println("url=" + url);
        Class.forName(driver);
        return DriverManager.getConnection(
                url, username, password
        );
    }

    private void showMenu(List<UserAction> actions) {
        AtomicInteger i = new AtomicInteger();
        out.println("Menu.");
        actions.forEach(a -> out.println(String.format("%d. %s", i.getAndIncrement(), a.name())));
    }

    public void init(Input input, Store tracker, List<UserAction> actions) {
        boolean run = true;
        while (run) {
            showMenu(actions);
            int select = input.askInt("Select:");
            if (select < 0 || select >= actions.size()) {
                out.println("Wrong input, you can select: 0 .. " + (actions.size() - 1));
                continue;
            }
            run = actions.get(select).execute(input, tracker);
        }
    }

    public static void main(String[] args) {
        try (Connection connection = loadConnection()) {
            Output output = new ConsoleOutput();
            Input input = new ConsoleInput();
            Input validate = new ValidateInput(output, input);
            Store tracker = new SqlTracker(connection);
            List<UserAction> actions = List.of(
                    new CreateAction(output),
                    new ShowAllAction(output),
                    new ReplaceAction(output),
                    new DeleteAction(output),
                    new FindByIdAction(output),
                    new FindByNameAction(output),
                    new ExitAction());
            new StartUI(output).init(validate, tracker, actions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
