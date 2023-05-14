package ru.job4j.tracker.action;

import org.junit.Test;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.output.Output;
import ru.job4j.tracker.output.StubOutput;
import ru.job4j.tracker.store.MemTracker;
import ru.job4j.tracker.store.Store;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeleteActionTest {
    @Test
    public void whenDeleteItemIsDone() {
        Output out = new StubOutput();
        Store tracker = new MemTracker();
        tracker.add(new Item("Deleted item"));
        DeleteAction delete = new DeleteAction(out);

        Input input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(1);
        delete.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(out.toString(), is("=== Delete item ====" + ln
                + "Data was successfully deleted." + ln));
        assertThat(tracker.findAll().size(), is(0));
    }

    @Test
    public void whenDeleteItemIsNotDone() {
        Output out = new StubOutput();
        Store tracker = new MemTracker();
        tracker.add(new Item("Deleted item"));
        DeleteAction delete = new DeleteAction(out);

        Input input = mock(Input.class);
        delete.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(out.toString(), is("=== Delete item ====" + ln
                + "Data could not be deleted." + ln));
        assertThat(tracker.findAll().get(0).getName(), is("Deleted item"));
    }
}