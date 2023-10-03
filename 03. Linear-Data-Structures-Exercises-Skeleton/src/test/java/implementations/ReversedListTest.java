package implementations;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReversedListTest {
  private ReversedList<Integer> reversedList;

  @Before
  public void setUp() {
    this.reversedList = new ReversedList<>();

    for (int i = 0; i < 10; i++) {
      this.reversedList.add(i);
    }
  }

  @Test
  public void add() {
    this.reversedList.add(11);
    Assert.assertEquals(11, (int) reversedList.get(0));
  }

  @Test
  public void size() {
    Assert.assertEquals(10, this.reversedList.size());
  }

  @Test
  public void ensureInitialCapacity() {
    ReversedList<String> initialCapacityList = new ReversedList<>();

    Assert.assertEquals(2, initialCapacityList.capacity());
  }

  @Test
  public void capacity() {
    Assert.assertEquals(16, this.reversedList.capacity());
  }

  @Test
  public void get() {
    Assert.assertEquals(0, (int) this.reversedList.get(9));
  }

  @Test
  public void removeAt() {
    this.reversedList.removeAt(0);

    Assert.assertEquals(8, (int) this.reversedList.get(0));
  }

  @Test
  public void iterator() {
    int index = 9;

    for (int e: this.reversedList) {
      Assert.assertEquals(index--, e);
    }
  }
}