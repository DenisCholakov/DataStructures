package implementations;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayDequeTest {

    @Test
    public void testAdd() {
        ArrayDeque<Integer> deque  = new ArrayDeque<>();

        deque.add(13);
        deque.add(14);
        deque.add(15);
        deque.add(16);
        deque.add(17);
    }

    @Test
    public void testAddFirst() {
        ArrayDeque<Integer> deque  = new ArrayDeque<>();

        deque.addFirst(13);
        deque.addFirst(14);
        deque.addFirst(15);
        deque.addFirst(16);
        deque.addFirst(17);
        deque.addFirst(18);
    }

}