package implementations;

import interfaces.Reversed;
import java.util.Arrays;
import java.util.Iterator;

public class ReversedList<E> implements Reversed<E> {
  private final int INITIAL_CAPACITY = 2;
  private Object[] elements;
  private int size;

  public ReversedList() {
    this.elements = new Object[INITIAL_CAPACITY];
    this.size = 0;
  }

  @Override
  public void add(E element) {
    if (this.size >= this.elements.length) {
      this.grow();
    }

    this.elements[size++] = element;
  }

  @Override
  public int size() {
    return this.size;
  }

  @Override
  public int capacity() {
    return this.elements.length;
  }

  @SuppressWarnings("unchecked")
  @Override
  public E get(int index) {
    this.checkIndex(index);

    return (E) this.elements[size - index - 1];
  }

  @SuppressWarnings("unchecked")
  @Override
  public E removeAt(int index) {
    this.checkIndex(index);
    E elementToRemove = (E) this.elements[this.size - index - 1];

    this.shiftLeft(index);
    this.elements[--size] = null;

    return elementToRemove;
  }

  @Override
  public Iterator<E> iterator() {
    return new Iterator<>() {
      private int index = size - 1;

      @Override
      public boolean hasNext() {
        return index >= 0;
      }

      @SuppressWarnings("unchecked")
      @Override
      public E next() {
        return (E) elements[index--];
      }
    };
  }

  private void grow() {
    this.elements = Arrays.copyOf(this.elements, this.elements.length * 2);
  }

  private void checkIndex(int index) {
    if (index >= this.size) {
      throw new IndexOutOfBoundsException("Index " + index + " is invalid.");
    }
  }
  private void shiftLeft(int index) {
    int realIndex = this.size - index - 1;

    for (int i = realIndex; i < size; i++) {
      this.elements[i] = this.elements[i + 1];
    }
  }

}
