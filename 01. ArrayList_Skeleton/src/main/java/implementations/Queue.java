package implementations;

import interfaces.AbstractQueue;

import java.util.Iterator;

public class Queue<E> implements AbstractQueue<E> {
    private static class Node<E> {
        private E value;
        private Node<E> next;

        public Node(E value) {
            this.value = value;
            this.next = null;
        }
    }

    private Node<E> head;
    private int size;

    public Queue() {
        this.head = null;
        this.size = 0;
    }


    @Override
    public void offer(E element) {
        Node<E> newNode = new Node<>(element);

        if (this.size == 0) {
            this.head = newNode;
        } else {
            Node<E> current = this.head;

            while (current.next != null) {
                current = current.next;
            }

            current.next = newNode;
        }

        size++;
    }

    @Override
    public E poll() {
        this.checkIfEmpty();

        Node<E> resultNode = this.head;
        this.head = this.head.next;
        resultNode.next = null;
        size--;

        return resultNode.value;
    }

    @Override
    public E peek() {
        this.checkIfEmpty();

        return this.head.value;
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
            private Node<E> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                E value = this.current.value;
                this.current = this.current.next;

                return value;
            }
        };
    }

    private void checkIfEmpty() {
        if (this.size == 0) {
            throw new IllegalStateException("Cannot poll element form empty queue.");
        }
    }
}
