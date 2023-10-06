package implementations;

import interfaces.AbstractTree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class Tree<E> implements AbstractTree<E> {
    private E value;
    private Tree<E> parent;
    private List<Tree<E>> children;

    public Tree(E value, Tree<E>... children) {
        this.value = value;
        this.children = new ArrayList<>();

        for (Tree<E> child: children) {
            child.setParent(this);
            this.addChild(child);
        }
    }

    @Override
    public void setParent(Tree<E> parent) {
        if (this.parent != null) {
            this.parent.children.remove(this);
        }

        this.parent = parent;
        parent.children.add(this);
    }

    @Override
    public void addChild(Tree<E> child) {
        if (child.parent != null) {
            child.parent.children.remove(child);
        }

        child.parent = this;
        this.children.add(child);
    }

    @Override
    public Tree<E> getParent() {
        return this.parent;
    }

    @Override
    public E getKey() {
        return this.value;
    }

    @Override
    public String getAsString() {
        StringBuilder builder = new StringBuilder();

        dfs(0, this, builder);

        return builder.toString().trim();
    }

    @Override
    public List<E> getLeafKeys() {
        Deque<Tree<E>> queue = new ArrayDeque<>();
        List<E> result = new ArrayList<>();
        queue.offer(this);

        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();

            for (Tree<E> child: current.children) {
                queue.offer(child);
            }

            if (current.children.isEmpty()) {
                result.add(current.value);
            }
        }

        return result.stream().sorted().collect(Collectors.toList());
    }

    @Override
    public List<E> getMiddleKeys() {
        Deque<Tree<E>> queue = new ArrayDeque<>();
        List<E> result = new ArrayList<>();
        queue.offer(this);

        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();

            for (Tree<E> child: current.children) {
                queue.offer(child);
            }

            if (current.parent != null && !current.children.isEmpty()) {
                result.add(current.value);
            }
        }

        return result.stream().sorted().collect(Collectors.toList());
    }

    @Override
    public Tree<E> getDeepestLeftmostNode() {
        Deque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);
        Tree<E> deepestNode = this;
        int deepestLevel = 0;

        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();

            for (Tree<E> child: current.children) {
                int childDepth = child.getDepth();

                if (childDepth > deepestLevel) {
                    deepestLevel = childDepth;
                    deepestNode = child;
                }

                queue.offer(child);
            }
        }

        return deepestNode;
    }

    @Override
    public List<E> getLongestPath() {
        if (this.parent == null && this.children.isEmpty()) {
            return null;
        }

        List<E> result = new ArrayList<>();
        this.getLongestPathDfs(result, this);

        return result;
    }

    @Override
    public List<List<E>> pathsWithGivenSum(int sum) {
        return this.getAllNodes()
                .stream()
                .filter(t -> t.getPathSum() == sum)
                .map(Tree::getPath)
                .collect(Collectors.toList());
    }

    @Override
    public List<Tree<E>> subTreesWithGivenSum(int sum) {
        return this.getAllNodes().stream().filter(t -> t.getTreeSum() == sum).collect(Collectors.toList());
    }

    private void dfs(int indentation, Tree<E> node, StringBuilder builder) {
        builder.append(" ".repeat(indentation) + String.valueOf(node.value));
        builder.append(System.lineSeparator());

        for (Tree<E> child: node.children) {
            this.dfs(indentation + 2, child, builder);
        }
    }

    private int getDepth() {
        int result = 0;
        Tree<E> current= this;

        while (current.parent != null) {
            result++;
            current = current.parent;
        }

        return result;
    }
    private void getLongestPathDfs(List<E> path, Tree<E> node) {
        if (node.getDepth() + 1 > path.size()) {
            path.add(node.value);
        }

        for (Tree<E> child: node.children) {
            this.getLongestPathDfs(path, child);
        }
    }

    private int getPathSum() {
        int sum = 0;

        Tree<E> current = this;

        while (current != null) {
            sum += (int) current.value;
            current = current.parent;
        }

        return sum;
    }

    private List<E> getPath() {
        Tree<E> current = this;
        Deque<E> stack = new ArrayDeque<>();
        List<E> result = new ArrayList<>();

        while (current != null) {
            stack.push(current.value);
            current = current.parent;
        }

        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }

        return result;
    }

    private List<Tree<E>> getAllNodes() {
        Deque<Tree<E>> queue = new ArrayDeque<>();
        List<Tree<E>> result = new ArrayList<>();
        queue.offer(this);

        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();
            result.add(current);

            for (Tree<E> child: current.children) {
                queue.offer(child);
            }
        }

        return result;
    }

    private List<E> getTreeDfs(Tree<E> node) {
        List<E> result = new ArrayList<>();

        result.add(node.value);

        for (Tree<E> child: node.children) {
            result.addAll(getTreeDfs(child));
        }

        return result;
    }

    private int getTreeSum() {
        Deque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);
        int sum = 0;

        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();
            sum += (int) current.value;

            for (Tree<E> child: current.children) {
                queue.offer(child);
            }
        }

        return sum;
    }
}



