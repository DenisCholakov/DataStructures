package implementations;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ArrayDequeTest {
    private ArrayDeque<String> deque;

    @Before
    public void setUp() {
        this.deque = new ArrayDeque<>();

        for (int i = 0; i < 10; i++) {
            this.deque.add(String.valueOf(i));
        }
    }

    @Test
    public void testAdd() {
        this.deque.add("55");
        Assert.assertEquals("55", this.deque.get(10));
    }

    @Test
    public void testAddFirst() {
        this.deque.addFirst("666");
        Assert.assertEquals("666", this.deque.get(0));
    }

    @Test
    public void removeAtIndexShouldWorkCorrectly() {
        Assert.assertEquals("2", this.deque.remove(2));
        Assert.assertEquals("8", this.deque.remove(7));

        Assert.assertEquals("3", this.deque.get(2));
        Assert.assertEquals("1", this.deque.get(1));
        Assert.assertEquals("0", this.deque.get(0));
        Assert.assertEquals("9", this.deque.get(7));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeAtWithInvalidIndexShouldThrowException() {
        this.deque.remove(22);
    }

    @Test
    public void removeShouldWorkCorrectly() {
        Assert.assertEquals("4", this.deque.remove("4"));
        Assert.assertNull(this.deque.remove("55"));

        Assert.assertNull("4", this.deque.get("4"));

    }

    @Test
    public void insertAtShouldWorkCorrectly() {
        this.deque.insert(2, "555");
        this.deque.insert(8, "666");

        Assert.assertEquals("2", this.deque.get(2));
        Assert.assertEquals("555", this.deque.get(3));
        Assert.assertEquals("3", this.deque.get(4));
        Assert.assertEquals("6", this.deque.get(7));
        Assert.assertEquals("666", this.deque.get(8));
        Assert.assertEquals("7", this.deque.get(9));
    }

    @Test
    public void resizeShouldWorkCorrectly() {
        this.deque.trimToSize();

        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(String.valueOf(i), this.deque.get(i));
        }
    }

    @Test
    public void IteratorShouldWorkCorrectly() {
        int index = 0;
        for (String e: this.deque) {
            Assert.assertEquals(String.valueOf(index++), e);
        }
    }

    @Test
    public void shouldWorkCorrectlyAsStack() {
        ArrayDeque<Integer> stack = new ArrayDeque<>();

        stack.push(5);
        stack.push(4);
        stack.push(3);
        stack.push(2);
        stack.push(1);
        stack.push(0);

        for (int i = 0; i < 6; i++) {
            Assert.assertEquals(i, (int) stack.pop());
        }
    }

    @Test
    public void shouldWorkCorrectlyAsQueue() {
        ArrayDeque<Integer> queue = new ArrayDeque<>();

        queue.offer(0);
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        queue.offer(5);

        for (int i = 0; i < 6; i++) {
            Assert.assertEquals(i, (int) queue.poll());
        }
    }

    @Test
    public void getObjectShouldWorkCorrectly() {
        Assert.assertEquals("4", deque.get("4"));
        Assert.assertNull(deque.get("11"));
    }
}