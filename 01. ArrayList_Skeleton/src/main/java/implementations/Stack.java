package implementations;

import interfaces.AbstractStack;

import java.util.Iterator;

public class Stack<E> implements AbstractStack<E> {
    private static class Node<E> {
        private E value;
        private Node<E> previous;

        public Node(E value) {
            this.value = value;
            this.previous = null;
        }
    }

    private Node<E> tail;
    private int size;

    public Stack() {
        this.tail = null;
        this.size = 0;
    }

    @Override
    public void push(E element) {
        Node<E> toAdd = new Node<>(element);
        toAdd.previous = this.tail;
        this.tail = toAdd;
        this.size++;
    }

    @Override
    public E pop() {
        this.checkIfEmpty();

        Node<E> result = this.tail;
        this.tail = result.previous;
        result.previous = null;
        this.size--;

        return result.value;
    }

    @Override
    public E peek() {
        this.checkIfEmpty();
        return this.tail.value;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = tail;
            @Override
            public boolean hasNext() {
                return this.current != null;
            }

            @Override
            public E next() {
                E value = this.current.value;
                this.current = this.current.previous;
                return value;
            }
        };
    }

    private void checkIfEmpty() {
        if (this.isEmpty()) {
            throw new IllegalStateException("Can not get element from an empty Stack.");
        }
    }
}
