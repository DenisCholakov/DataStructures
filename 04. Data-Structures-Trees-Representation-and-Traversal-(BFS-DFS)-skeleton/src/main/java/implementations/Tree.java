package implementations;

import interfaces.AbstractTree;

import java.util.*;

public class Tree<E> implements AbstractTree<E> {
    private E value;
    private Tree<E> parent;
    private List<Tree<E>> children;

    public Tree(E value, Tree<E>... subtrees) {
        this.value = value;
        this.parent = null;
        this.children = new ArrayList<>();

        for (Tree<E> subtree: subtrees) {
            this.children.add(subtree);
            subtree.parent = this;
        }
    }

    @Override
    public List<E> orderBfs() {
        List<E> result = new ArrayList<>();

        if (this.value == null) {
            return result;
        }

        ArrayDeque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);

        while(!queue.isEmpty()) {
            Tree<E> current = queue.poll();

            for (Tree<E> child: current.children) {
                queue.offer(child);
            }

            result.add(current.value);
        }

        return result;
    }

    @Override
    public List<E> orderDfs() {
        if (this.value == null) {
            return new ArrayList<>();
        }

        List<E> result = this.doDfs(this);
        return result;
    }

    @Override
    public void addChild(E parentKey, Tree<E> child) {
        Tree<E> parent = this.find(parentKey);

        if (parent == null) {
            throw new IllegalArgumentException("Node is not present");
        }

        parent.children.add(child);
        child.parent = parent;
    }

    @Override
    public void removeNode(E nodeKey) {
        Tree<E> toRemove = find(nodeKey);

        if (toRemove == null) {
            throw new IllegalArgumentException();
        }

        Deque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(toRemove);
        Tree<E> parent = toRemove.parent;

        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();

            for (Tree<E> child: current.children) {
                queue.push(child);
            }

            current.parent = null;
            current.children.clear();
        }

        if (parent != null) {
            parent.children.remove(toRemove);
        }

        toRemove.value = null;
    }

    @Override
    public void swap(E firstKey, E secondKey) {
        Tree<E> firstNode = this.find(firstKey);
        Tree<E> secondNode = this.find(secondKey);

        if (firstNode == null || secondNode == null) {
            throw new IllegalArgumentException("Invalid node");
        }

        Tree<E> firstParent = firstNode.parent;
        Tree<E> secondParent = secondNode.parent;

        if (firstParent == null) {
            swapRoot(secondNode);
            return;
        } else if(secondParent == null) {
            swapRoot(firstNode);
            return;
        }

        firstNode.parent = secondParent;
        secondNode.parent = firstParent;

        int firstIndex = firstParent.children.indexOf(firstNode);
        int secondIndex = secondParent.children.indexOf(secondNode);
        firstParent.children.set(firstIndex, secondNode);
        secondParent.children.set(secondIndex, firstNode);
    }

    private List<E> doDfs(Tree<E> node) {
        List<E> result = new ArrayList<>();

        for (Tree<E> child: node.children) {
            result.addAll(this.doDfs(child));
        }

        result.add(node.value);

        return result;
    }

    private Tree<E> find(E parentKey) {
        List<E> result = new ArrayList<>();
        Deque<Tree<E>> stack = new ArrayDeque<>();
        stack.push(this);

        while(!stack.isEmpty()) {
            Tree<E> current = stack.pop();

            if (current.value.equals(parentKey)) {
                return current;
            }

            for (Tree<E> node: current.children) {
                stack.push(node);
            }
        }

        return null;
    }

    private void swapRoot(Tree<E> node) {
        this.value = node.value;
        this.parent = null;
        this.children = node.children;
        node.parent = null;

    }
}



