package ru.job4j.tracker.store;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.tracker.model.Item;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;

public class SqlTrackerTest {

    private static Connection connection;

    @BeforeClass
    public static void initConnection() {
        try (InputStream in =
                     SqlTrackerTest.class.getClassLoader().getResourceAsStream("test.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")

            );
            connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS items ("
                            + " id serial primary key,"
                            + " name text,"
                            + " created timestamp "
                            + ");"
            ).executeUpdate();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @AfterClass
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @After
    public void wipeTable() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("delete from items")) {
            statement.execute();
        }
    }

    @Test
    public void whenSaveItemAndFindByGeneratedIdThenMustBeTheSame() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        assertThat(tracker.findById(item.getId()), is(item));
    }

    @Test
    public void whenSaveAndReplaceItemThen() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        int id = tracker.add(item).getId();
        Item expected = new Item(id, "new item");
        assertTrue(tracker.replace(id, expected));
        assertThat(tracker.findById(id), is(expected));
    }

    @Test
    public void whenTryToChangeButThereIsNoElement() {
        SqlTracker tracker = new SqlTracker(connection);
        assertFalse(tracker.replace(1, new Item("Item")));
    }

    @Test
    public void whenSaveAndDeleteItemThenMustChange() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        int id = tracker.add(item).getId();
        assertTrue(tracker.delete(id));
        assertFalse(tracker.delete(id));
        assertThat(tracker.findById(id), is(nullValue()));
    }

    @Test
    public void whenTryToDeleteButThereIsNoElements() {
        SqlTracker tracker = new SqlTracker(connection);
        assertFalse(tracker.delete(1));
    }

    @Test
    public void whenAdd3ItemsThenFindAll() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item1 = new Item("Item 1");
        Item item2 = new Item("Item 2");
        Item item3 = new Item("Item 3");
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        List<Item> items = List.of(
                item1,
                item2,
                item3
        );
        assertThat(tracker.findAll(), is(items));
    }

    @Test
    public void whenAdd3ItemsIncluding2WithSameNameThenFindByName() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item1 = new Item("Item 1");
        Item item2 = new Item("Item 1");
        Item item3 = new Item("Item 2");
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        List<Item> items = List.of(
                item1,
                item2
        );
        assertThat(tracker.findByName("Item 1"), is(items));
    }

    @Test
    public void whenThereIsNoItemThenFindById() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("test");
        int existingId = tracker.add(item).getId();
        assertNull(tracker.findById(existingId + 1));
    }
}