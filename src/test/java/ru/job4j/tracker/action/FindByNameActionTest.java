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

public class FindByNameActionTest {
    @Test
    public void whenFindByNameIsDone() {
        Output out = new StubOutput();
        Store tracker = new MemTracker();
        Item item1 = new Item("Searched item");
        Item item2 = new Item("Searched item");
        tracker.add(item1);
        tracker.add(item2);
        FindByNameAction findByName = new FindByNameAction(out);

        Input input = mock(Input.class);
        when(input.askStr(any(String.class))).thenReturn("Searched item");

        findByName.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(out.toString(), is("=== Find items by name ====" + ln
                + item1 + ln
                + item2 + ln
        ));
    }

    @Test
    public void whenFindByNameIsNotDone() {
        Output out = new StubOutput();
        Store tracker = new MemTracker();
        FindByNameAction findByName = new FindByNameAction(out);
        String findName = "Searched item";

        Input input = mock(Input.class);
        when(input.askStr(any(String.class))).thenReturn(findName);

        findByName.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(out.toString(), is("=== Find items by name ====" + ln
                + "Заявки с именем: " + findName + " не найдены." + ln));
    }
}