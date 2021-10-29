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

public class FindByIdActionTest {
    @Test
    public void whenFindByIdDone() {
        Output out = new StubOutput();
        Store tracker = new MemTracker();
        Item item = new Item("Searched item");
        tracker.add(item);
        FindByIdAction findById = new FindByIdAction(out);

        Input input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(1);

        findById.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(out.toString(), is("=== Find item by id ====" + ln + item + ln));
    }

    @Test
    public void whenFindByIdIsNotDone() {
        Output out = new StubOutput();
        Store tracker = new MemTracker();
        FindByIdAction findById = new FindByIdAction(out);

        Input input = mock(Input.class);

        findById.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(out.toString(), is("=== Find item by id ====" + ln
                + "Заявка с введенным id: 0 не найдена." + ln));
    }
}