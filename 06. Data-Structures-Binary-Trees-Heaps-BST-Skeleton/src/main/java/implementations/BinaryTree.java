package implementations;

import interfaces.AbstractBinaryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BinaryTree<E> implements AbstractBinaryTree<E> {
    private E key;
    private BinaryTree<E> leftChild;
    private BinaryTree<E> rightChild;

    public BinaryTree(E key, BinaryTree<E> leftChild, BinaryTree<E> rightChild) {
        this.key = key;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    @Override
    public E getKey() {
        return this.key;
    }

    @Override
    public AbstractBinaryTree<E> getLeft() {
        return this.leftChild;
    }

    @Override
    public AbstractBinaryTree<E> getRight() {
        return this.rightChild;
    }

    @Override
    public void setKey(E key) {
        this.key = key;
    }

    @Override
    public String asIndentedPreOrder(int indent) {
        StringBuilder builder  = new StringBuilder();
        builder.append(" ".repeat(indent) + this.getKey());

        if (this.leftChild != null) {
            builder.append(System.lineSeparator());
            builder.append(this.leftChild.asIndentedPreOrder(indent + 2));
        }

        if (this.rightChild != null) {
            builder.append(System.lineSeparator());
            builder.append(this.rightChild.asIndentedPreOrder(indent + 2));
        }

        var a  = builder.toString();


        return builder.toString();
    }

    @Override
    public List<AbstractBinaryTree<E>> preOrder() {
        return this.preOrderDfs(this);
    }

    @Override
    public List<AbstractBinaryTree<E>> inOrder() {
        List<AbstractBinaryTree<E>> result = new ArrayList<>();

        if (this.getLeft() != null) {
            result.addAll(this.getLeft().inOrder());
        }

        result.add(this);

        if (this.getRight() != null) {
            result.addAll(this.getRight().inOrder());
        }

        return result;
    }

    @Override
    public List<AbstractBinaryTree<E>> postOrder() {
        List<AbstractBinaryTree<E>> result = new ArrayList<>();

        if (this.getLeft() != null) {
            result.addAll(this.getLeft().postOrder());
        }

        if (this.getRight() != null) {
            result.addAll(this.getRight().postOrder() );
        }

        result.add(this);

        return result;
    }

    @Override
    public void forEachInOrder(Consumer<E> consumer) {
        if (this.getLeft() != null) {
            this.getLeft().forEachInOrder(consumer);
        }

        consumer.accept(this.getKey());

        if (this.getRight() != null) {
            this.getRight().forEachInOrder(consumer);
        }
    }

    private List<AbstractBinaryTree<E>> preOrderDfs(AbstractBinaryTree<E> node) {
        List<AbstractBinaryTree<E>> result = new ArrayList<>();
        if (node != null) {
            result.add(node);
            result.addAll(this.preOrderDfs(node.getLeft()));
            result.addAll(this.preOrderDfs(node.getRight()));
        }

        return result;
    }
}
