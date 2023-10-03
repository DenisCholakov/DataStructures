package implementations;

import interfaces.LinkedList;

import java.util.Iterator;

public class SinglyLinkedList<E> implements LinkedList<E> {
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

    public SinglyLinkedList() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public void addFirst(E element) {
        Node<E> toAdd = new Node<>(element);

        toAdd.next = this.head;
        this.head = toAdd;
        this.size++;
    }

    @Override
    public void addLast(E element) {
        Node<E> toAdd = new Node<>(element);
        if (this.size == 0) {
            this.head = toAdd;
        } else {
            var lastNode = this.getLastNode();
            lastNode.next = toAdd;
        }

        size++;
    }

    @Override
    public E removeFirst() {
        this.checkIfEmpty();

        var toRemove = this.head;
        this.head = this.head.next;
        toRemove.next = null;
        size--;

        return toRemove.value;
    }

    @Override
    public E removeLast() {
        this.checkIfEmpty();

        var current = this.head;

        while(current.next.next != null) {
            current = current.next;
        }

        var toRemove = current.next;
        current.next = null;
        size--;

        return toRemove.value;
    }

    @Override
    public E getFirst() {
        return this.head.value;
    }

    @Override
    public E getLast() {

        return this.getLastNode().value;
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
                E result = current.value;
                current = current.next;

                return result;
            }
        };
    }

    private void checkIfEmpty() {
        if (this.size == 0) {
            throw new IllegalStateException("The List is empty.");
        }
    }

    private Node<E> getLastNode() {
        var current = this.head;

        while (current.next != null) {
            current = current.next;
        }

        return current;
    }
}
