package implementations;

import interfaces.List;

import java.util.Arrays;
import java.util.Iterator;

public class ArrayList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 2;
    private int size = 0;
    private Object[] elements;

    public ArrayList() {
        this.elements = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public boolean add(E element) {
        if (this.size >= elements.length) {
            this.grow();
        }

        this.elements[this.size++] = element;
        return true;
    }

    @Override
    public boolean add(int index, E element) {
        checkIfIndexIsValid(index);

        if (this.size >= elements.length) {
            this.grow();
        }

        this.shiftRight(index);
        this.elements[index] = element;
        this.size++;
        return true;
    }

    @Override
    public E get(int index) {
        this.checkIfIndexIsValid(index);

        return (E) this.elements[index];
    }

    @Override
    public E set(int index, E element) {
        checkIfIndexIsValid(index);

        if (this.size >= elements.length) {
            this.grow();
        }

        E oldElement = (E) this.elements[index];
        this.elements[index] = element;

        return oldElement;
    }

    @Override
    public E remove(int index) {
        checkIfIndexIsValid(index);

        if (this.elements.length < this.size / 3) {
            this.shrink();
        }

        E elementToRemove = (E) this.elements[index];
        this.shiftLeft(index);
        this.elements[size--] = null;

        return elementToRemove;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int indexOf(E element) {
        for (int i = 0; i < this.size; i++) {
            if (this.elements[i].equals(element)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public boolean contains(E element) {
        return this.indexOf(element) != -1;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int index = 0;
            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public E next() {
                return (E) elements[index++];
            }
        };
    }

    private void grow() {
        this.elements = Arrays.copyOf(this.elements, this.elements.length * 2);
    }

    private void shrink() {
        this.elements = Arrays.copyOf(this.elements, this.elements.length / 2);
    }

    private void shiftRight(int index) {
        for (int i = size; i > index ; i--) {
            this.elements[i] = this.elements[i - 1];
        }
    }

    private void shiftLeft(int index) {
        for (int i = index; i < this.elements.length - 1 ; i++) {
            this.elements[i] = this.elements[i + 1];
        }
    }

    private void checkIfIndexIsValid(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(String.format("Index out of bounds: %d for size %d", index, this.size));
        }
    }
}
