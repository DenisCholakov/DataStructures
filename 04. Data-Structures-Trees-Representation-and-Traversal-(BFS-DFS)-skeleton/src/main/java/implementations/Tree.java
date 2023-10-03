package implementations;

import interfaces.AbstractTree;

import java.util.ArrayList;
import java.util.List;

public class Tree<E> implements AbstractTree<E> {
    private E value;
    private Tree<E> parent;
    private List<Tree<E>> children;

    public Tree(E value) {
        this.value = value;
        this.parent = null;
        this.children = new ArrayList<>();
    }

    public Tree(E value, Tree<E>... subtrees) {
        this.value = value;
        this.parent = null;
        this.children = new ArrayList<>();

        for (Tree<E> subtree: subtrees) {
            this.children.add(subtree);
        }
    }

    @Override
    public List<E> orderBfs() {
        return null;
    }

    @Override
    public List<E> orderDfs() {
        return null;
    }

    @Override
    public void addChild(E parentKey, Tree<E> child) {

    }
	
	@Override
    public void removeNode(E nodeKey) {

    }

    @Override
    public void swap(E firstKey, E secondKey) {

    }
}



