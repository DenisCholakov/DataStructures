package implementations;

import interfaces.AbstractBinarySearchTree;

public class BinarySearchTree<E extends Comparable<E>> implements AbstractBinarySearchTree<E> {
    private Node<E> root;

    public BinarySearchTree() {}

    public  BinarySearchTree(Node<E> root) {
        this.copy(root);
    }

    private void copy(Node<E> node) {
        if (node != null) {
            this.insert(node.value);
            this.copy(node.leftChild);
            this.copy(node.rightChild);
        }
    }

    @Override
    public void insert(E element) {
        Node<E> newNode = new Node<>(element);
        if (this.getRoot() == null) {
            this.root = newNode;
        } else {
            Node<E> current = this.root;
            Node<E> previous = null;
            boolean isLeftChild = false;

            while (current != null) {
                previous = current;
                if (element.compareTo(current.value) < 0) {
                    current = current.leftChild;
                    isLeftChild = true;
                } else if (element.compareTo(current.value) > 0) {
                    current = current.rightChild;
                    isLeftChild = false;
                } else {
                    return;
                }
            }

            if (isLeftChild) {
                previous.leftChild = newNode;
            } else {
                previous.rightChild = newNode;
            }
        }


    }

    @Override
    public boolean contains(E element) {
        Node<E> current = this.root;

        while (current != null) {
            if (current.value.compareTo(element) < 0) {
                current = current.leftChild;
            } else if (current.value.compareTo(element) > 0) {
                current = current.rightChild;
            } else {
                return true;
            }
        }

        return false;
    }

    @Override
    public AbstractBinarySearchTree<E> search(E element) {
        AbstractBinarySearchTree<E> result = new BinarySearchTree<>();
        Node<E> current = this.root;

        while (current != null) {
            if (current.value.compareTo(element) > 0) {
                current = current.leftChild;
            } else if (current.value.compareTo(element) < 0) {
                current = current.rightChild;
            } else {
                return new BinarySearchTree<>(current);
            }
        }

        return result;
    }

    @Override
    public Node<E> getRoot() {
        return this.root;
    }

    @Override
    public Node<E> getLeft() {
        return this.root.leftChild;
    }

    @Override
    public Node<E> getRight() {
        return this.root.rightChild;
    }

    @Override
    public E getValue() {
        return null;
    }
}
