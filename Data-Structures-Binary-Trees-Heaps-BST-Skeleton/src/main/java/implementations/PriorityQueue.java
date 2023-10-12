package implementations;

import interfaces.AbstractQueue;
import org.apache.commons.codec.BinaryDecoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PriorityQueue<E extends Comparable<E>> implements AbstractQueue<E> {
    private List<E> elements;

    public PriorityQueue() {
        this.elements = new ArrayList<>();
    }


    @Override
    public int size() {
        return this.elements.size();
    }

    @Override
    public void add(E element) {
        this.elements.add(element);
        this.heapifyUp(this.size() - 1);
    }

    @Override
    public E peek() {
        ensureNotEmpty();

        return this.elements.get(0);
    }

    @Override
    public E poll() {
        this.ensureNotEmpty();
        E returnValue = this.elements.get(0);
        Collections.swap(this.elements, 0, this.size() - 1);
        this.elements.remove(this.size() - 1);
        this.heapifyDown(0);

        return returnValue;
    }

//    private void heapifyDown(int index) {
//        int biggestChildIndex = this.getBiggestChildIndex(index);
//
//        if (biggestChildIndex == -1) {
//            return;
//        }
//
//        while(getLeftChildIndex(index) < this.size() && biggestChildIndex > 0 && isLess(biggestChildIndex, index)) {
//            Collections.swap(this.elements, index, biggestChildIndex);
//            index = biggestChildIndex;
//            biggestChildIndex = this.getBiggestChildIndex(index);
//        }
//    }

    private void heapifyDown(int index) {
        while (index < this.elements.size() / 2) {
            int child = 2 * index + 1;

            if (child + 1 < this.elements.size() && this.isLess(child + 1, child)) {
                child++;
            }

            if (isLess(index, child)) {
                break;
            }

            Collections.swap(this.elements, index, child);
            index = child;
        }

    }

    private void heapifyUp(int index) {
        while (index > 0 && this.isLess(index, this.getParent(index))) {
            Collections.swap(this.elements, index, getParent(index));
            index = this.getParent(index);
        }
    }

    private int getParent(int index) {
        return (index - 1) / 2;
    }

    private boolean isLess(int index, int parentIndex) {
        return this.elements.get(index).compareTo(this.elements.get(parentIndex)) > 0;
    }

    private void ensureNotEmpty() {
        if (this.size() == 0) {
            throw new IllegalStateException("Queue is empty.");
        }
    }

    private int getBiggestChildIndex(int index) {
        if (this.getLeftChildIndex(index) >= this.size()) {
            return -1;
        } else if (this.getRightChildIndex(index) >= this.size() ||
                this.isLess(this.getLeftChildIndex(index),this.getRightChildIndex(index))) {
            return this.getLeftChildIndex(index);
        } else  {
            return this.getRightChildIndex(index);
        }
    }

    private E getLeftChild(int index) {
        return this.elements.get(this.getLeftChildIndex(index));
    }

    private E getRightChild(int index) {
        return this.elements.get(this.getRightChildIndex(index));
    }

    private int getLeftChildIndex(int index) {
        return index * 2 + 1;
    }

    private int getRightChildIndex(int index) {
        return index * 2 + 2;
    }
}
