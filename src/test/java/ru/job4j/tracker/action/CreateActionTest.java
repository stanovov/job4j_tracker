package ru.job4j.tracker.action;

import org.junit.Test;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.output.Output;
import ru.job4j.tracker.output.StubOutput;
import ru.job4j.tracker.store.MemTracker;
import ru.job4j.tracker.store.Store;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class CreateActionTest {
    @Test
    public void whenCreateItemIsDone() {
        String itemName = "New item";
        Output out = new StubOutput();
        Store tracker = new MemTracker();
        CreateAction create = new CreateAction(out);

        Input input = mock(Input.class);
        when(input.askStr(any(String.class))).thenReturn(itemName);
        create.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(tracker.findAll().get(0).getName(), is(itemName));
        assertThat(out.toString(), is("=== Create a new Item ====" + ln
                + "Добавленная заявка: " + tracker.findByName(itemName).get(0) + ln));
    }
}