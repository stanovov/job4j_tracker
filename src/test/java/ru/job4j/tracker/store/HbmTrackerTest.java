package ru.job4j.tracker.store;

import org.junit.Test;
import ru.job4j.tracker.model.Item;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;

public class HbmTrackerTest {

    @Test
    public void whenAddItemThenFindAll() {
        try (HbmTracker tracker = new HbmTracker()) {
            Item item = new Item("test item");
            int id = tracker.add(item).getId();
            List<Item> result = tracker.findAll();
            assertThat(result.size(), is(id));
            assertThat(result.get(id), is(item));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenAddItemAndReplaceItThenFindById() {
        try (HbmTracker tracker = new HbmTracker()) {
            Item item = new Item("test item");
            int id = tracker.add(item).getId();
            Item expected = new Item(id, "new test item");
            assertThat(tracker.replace(id, expected), is(true));
            assertThat(tracker.findById(id), is(expected));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenAddAndDeleteItemThenMustChange() {
        try (HbmTracker tracker = new HbmTracker()) {
            Item item = new Item("item");
            int id = tracker.add(item).getId();
            assertThat(tracker.delete(id), is(true));
            assertThat(tracker.delete(id), is(false));
            assertThat(tracker.findById(id), is(nullValue()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenTryToDeleteThenThereIsNoItems() {
        try (HbmTracker tracker = new HbmTracker()) {
            assertThat(tracker.delete(1), is(false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenAdd3ItemsThenFindAll() {
        try (HbmTracker tracker = new HbmTracker()) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenAdd3ItemsIncluding2WithSameNameThenFindByName() {
        try (HbmTracker tracker = new HbmTracker()) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenThereIsNoItemThenFindById() {
        try (HbmTracker tracker = new HbmTracker()) {
            Item item = new Item("test");
            int id = tracker.add(item).getId();
            assertThat(tracker.findById(id + 1), is(nullValue()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}