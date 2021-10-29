package ru.job4j.tracker.store;

import org.junit.Test;
import ru.job4j.tracker.model.Item;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;

public class MemTrackerTest {

    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        MemTracker tracker = new MemTracker();
        Item item = new Item();
        item.setName("test1");
        tracker.add(item);
        Item result = tracker.findById(item.getId());
        assertThat(result.getName(), is(item.getName()));
    }

    @Test
    public void whenReplace() {
        MemTracker tracker = new MemTracker();
        Item bug = new Item();
        bug.setName("Bug");
        tracker.add(bug);
        int id = bug.getId();
        Item bugWithDesc = new Item();
        bugWithDesc.setName("Bug with description");
        tracker.replace(id, bugWithDesc);
        assertThat(tracker.findById(id).getName(), is("Bug with description"));
    }

    @Test
    public void whenDelete() {
        MemTracker tracker = new MemTracker();
        Item bug = new Item();
        bug.setName("Bug");
        tracker.add(bug);
        int id = bug.getId();
        tracker.delete(id);
        assertThat(tracker.findById(id), is(nullValue()));
    }

    @Test
    public void whenDeleteMoreItems() {
        MemTracker tracker = new MemTracker();
        Item bug1 = new Item("Bug 1");
        tracker.add(bug1);
        Item bug2 = new Item("Bug 2");
        tracker.add(bug2);
        Item bug3 = new Item("Bug 3");
        tracker.add(bug3);
        int id = bug2.getId();
        tracker.delete(id);
        assertThat(tracker.findById(id), is(nullValue()));
    }
}