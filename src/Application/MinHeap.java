package Application;

public class MinHeap<T extends Comparable<T>> {
    private ArrayList<T> heap;

    public MinHeap() {
        this.heap = new ArrayList<>();
    }

    public void add(T item) {
        heap.add(item);
        int index = heap.size() - 1;
        heapifyUp(index);
    }

    public T poll() {
        if (heap.size() == 0) {
            return null;
        }
        T item = heap.get(0);
        T lastItem = heap.remove(heap.size() - 1);
        if (heap.size() > 0) {
            heap.set(0, lastItem);
            heapifyDown(0);
        }
        return item;
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    private void heapifyUp(int index) {
        T item = heap.get(index);
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            T parent = heap.get(parentIndex);
            if (item.compareTo(parent) >= 0) {
                break;
            }
            heap.set(index, parent);
            index = parentIndex;
        }
        heap.set(index, item);
    }
    

    private void heapifyDown(int index) {
        T item = heap.get(index);
        int size = heap.size();
        while (index < size) {
            int leftChildIndex = 2 * index + 1;
            int rightChildIndex = 2 * index + 2;
            if (leftChildIndex >= size) {
                break;
            }
            int smallerChildIndex = leftChildIndex;
            if (rightChildIndex < size && heap.get(rightChildIndex).compareTo(heap.get(leftChildIndex)) < 0) {
                smallerChildIndex = rightChildIndex;
            }
            if (item.compareTo(heap.get(smallerChildIndex)) <= 0) {
                break;
            }
            heap.set(index, heap.get(smallerChildIndex));
            index = smallerChildIndex;
        }
        heap.set(index, item);
    }
}
