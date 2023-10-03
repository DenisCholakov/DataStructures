package implementations;

import interfaces.Deque;

import java.util.Iterator;

public class ArrayDeque<E> implements Deque<E> {
    private final int INITIAL_CAPACITY = 7;
    private int size;
    private int head;
    private int tail;
    private Object[] elements;

    public ArrayDeque() {
        this.elements = new Object[INITIAL_CAPACITY];
        int middle = INITIAL_CAPACITY / 2;
        this.head = this.tail = middle;
    }

    @Override
    public void add(E element) {
        this.addLast(element);
    }

    @Override
    public void offer(E element) {
        this.addLast(element);
    }

    @Override
    public void addFirst(E element) {
        if (this.size == 0) {
            this.elements[tail] = element;
        } else {
            if (this.head == 0) {
                this.elements = this.grow();
            }

            this.elements[--this.head] = element;
        }

        this.size++;
    }

    @Override
    public void addLast(E element) {
        if (this.size == 0) {
            this.elements[tail] = element;
        } else {
            if (this.tail == this.elements.length - 1) {
                this.elements = this.grow();
            }

            this.elements[++this.tail] = element;
        }

        this.size++;
    }

    @Override
    public void push(E element) {
        this.addFirst(element);
    }

    @Override
    public void insert(int index, E element) {
        int realIndex = this.head + index;
        this.ensureIndex(realIndex);

        if (realIndex - this.head < this.tail - realIndex) {
            if (this.head == 0) {
                this.elements = this.grow();
                realIndex = this.head + index;
            }

            shiftLeft(this.head - 1, realIndex);
            this.elements[realIndex] = element;
            this.head--;
        } else {
            if (this.tail == this.elements.length - 1) {
                this.elements = this.grow();
                realIndex = this.head + index;
            }

            shiftRight(realIndex, this.tail + 1);
            this.elements[realIndex] = element;
            this.tail++;
        }

        this.size++;
    }

    @Override
    public void set(int index, E element) {
        int realIndex = this.head + index;
        this.ensureIndex(realIndex);
        this.elements[index] = element;
    }

    @Override
    public E peek() {
        if (this.size != 0) {
            return getAt(this.head);
        }

        return null;
    }

    @Override
    public E poll() {
        return removeFirst();
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public E get(int index) {
        int realIndex = this.head + index;
        this.ensureIndex(realIndex);
        return this.getAt(realIndex);
    }

    @Override
    public E get(Object object) {
        if (this.isEmpty()) {
            return null;
        }

        for (int i = this.head; i <= this.tail; i++) {
            if (this.elements[i] != null && this.elements[i].equals(object)) {
                return this.getAt(i);
            }
        }

        return null;
    }

    @Override
    public E remove(int index) {
        int realIndex = this.head + index;
        this.ensureIndex(realIndex);
        E elementToRemove = this.getAt(realIndex);

        if (realIndex - this.head < this.tail - realIndex) {
            this.shiftRight(this.head, realIndex);
            elements[this.head++] = null;
        } else {
            this.shiftLeft(realIndex, this.tail);
            elements[this.tail--] = null;
        }

        this.size--;

        return elementToRemove;
    }

    @Override
    public E remove(Object object) {
        if (this.isEmpty()) {
            return null;
        }

        for (int i = this.head; i <= this.tail; i++) {
            if (this.elements[i] != null && this.elements[i].equals(object)) {
                E elementToRemove = this.getAt(i);
                this.remove(i - this.head);
                return elementToRemove;
            }
        }
        return null;
    }

    @Override
    public E removeFirst() {
        if (!this.isEmpty()) {
            E element = this.getAt(this.head);
            this.elements[this.head++] = null;
            this.size--;
            return element;
        }

        return null;
    }

    @Override
    public E removeLast() {
        if (!this.isEmpty()) {
            E element = this.getAt(this.tail);
            this.elements[this.tail--] = null;
            this.size--;
            return element;
        }

        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int capacity() {
        return this.elements.length;
    }

    @Override
    public void trimToSize() {

        Object[] newElements = new Object[this.size % 2 != 0 ? this.size * 2 : this.size * 2 + 1];
        int index = 0;

        for (int i = this.head; i <= this.tail; i++) {
            newElements[index++] = this.elements[i];
        }
        this.head= 0;
        this.tail = this.size - 1;
        this.elements = newElements;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int index = head;

            @Override
            public boolean hasNext() {
                return index != tail + 1;
            }

            @Override
            public E next() {
                return getAt(index++);
            }
        };
    }

    private Object[] grow() {
        int newCapacity = this.elements.length * 2 + 1;
        Object[] newElements = new Object[newCapacity];
        int middle = newCapacity / 2;
        int begin = middle - this.size / 2;
        int index = this.head;

        for (int i = begin; i <= begin + this.size - 1; i++) {
            newElements[i] = this.elements[index++];
        }

        this.head = begin;
        this.tail = this.head + this.size - 1;

        return  newElements;
    }

    @SuppressWarnings("unchecked")
    private E getAt(int index) {
        return (E) this.elements[index];
    }

    private void ensureIndex(int index) {
        if (index > this.tail || index < this.head) {
            throw new IndexOutOfBoundsException("Invalid index - " + (index - this.head) + ".");
        }
    }

    private void shiftLeft(int startIndex, int endIndex) {
        for (int i = startIndex; i < endIndex; i++) {
            this.elements[i] = this.elements[i + 1];
        }
    }

    private void shiftRight(int startIndex, int endIndex) {
        for (int i = endIndex; i > startIndex; i--) {
            this.elements[i] = this.elements[i - 1];
        }
    }
}
