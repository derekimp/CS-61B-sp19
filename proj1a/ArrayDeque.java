public class ArrayDeque<T> {


    /**
     * Parameters needed in the array deque.
     */
    private int size; // number of items in the array.
    private int capacity; // maximum capacity of the array.
    private int nextFirst; // the index of empty box that just before the first element of the array.
    private int nextLast; // the index of the empty box that just after the last element in the array.
    private T[] array; // array container.

    /**
     * Normal constructor.
     */
    public ArrayDeque(T first) {
        capacity = 8;
        size = 1;
        nextFirst = 3;
        nextLast = 5;
        array = (T[]) new Object[capacity];
        array[nextFirst+1] = first;
    }

    /**
     * Empty constructor.
     */
    public ArrayDeque() {
        capacity = 8;
        size = 0;
        nextFirst = 3;
        nextLast = 4;
        array = (T[]) new Object[capacity];
    }

    /**
     * Copy constructor.
     */
    public ArrayDeque(ArrayDeque other) {
        capacity = other.getCapacity();
        size = other.size();
        array = (T[]) new Object[capacity];
        System.arraycopy(other, 0, array, 0, capacity);

        nextFirst = other.getFirst();
        nextLast = other.getLast();

    }

    /**
     * When one of the two following condition applied, resize the capacity
     * of the array in order to save memory/increase capacity:
     * 1. when size == capacity but new item needed to be added to array
     *    new capacity  = 2 * capacity;
     * 2. when size/capacity < 0.25, new capacity  = 1/2 * capacity;
     * @param newCapacity new maximum number of items that array could hold
     */
    private void expand(int newCapacity) {

        // x x x x | x x x x
        //    nextF  nextL
        // x x x x | y y y y y y y y | x x x x
        //      nextF   capacity      nextL

        T[] new_array = (T[]) new Object[newCapacity];
        int diff = newCapacity - capacity;
        System.arraycopy(array, 0, new_array, 0, nextLast);
        System.arraycopy(array, nextLast, new_array, diff + nextLast, diff - nextFirst - 1);

        nextLast += diff;
        array = new_array;
        capacity = newCapacity;
    }

    private void shrink(int newCapacity) {
        T[] old_array = array;
        array = (T[]) new Object[newCapacity];
        // x x x x x x x x
        // y y y y

        int newFirst = newCapacity/2;
        int newLast = newFirst - 1;

        if (nextFirst == capacity - 1) {
            for (int i = 0; i < size(); i += 1) {
                addLast(old_array[i]);
            }
        } else if (nextFirst > nextLast) {
            for (int i = nextFirst + 1; i < capacity; i += 1) {
                addLast(old_array[i]);
            }
            for (int i = 0; i < nextLast; i += 1) {
                if (old_array[i] != null) {
                    addLast(old_array[i]);
                }
            }
        } else {
            for (int i = nextFirst + 1; i < nextLast; i += 1) {
                if (old_array[i] != null) {
                    addLast(old_array[i]);
                }
            }
        }

        capacity = newCapacity;
    }


    /**
     * Insert an item to the first position in the list, but after the sentinel node.
     * @param item
     */
    public void addFirst(T item) {
        if (size == capacity) {
            expand(2 * capacity);
        }

        array[nextFirst] = item;
        if (nextFirst >= 1) {
            nextFirst -= 1;
        } else {
            nextFirst = capacity - 1;
        }
        size += 1;

    }

    /**
     * Insert an item to the last position in the list, followed by the sentinel node.
     * @param item
     */
    public void addLast(T item) {
        if (size == capacity) {
            expand(2 * capacity);
        }

        array[nextLast] = item;
        if (nextLast == capacity - 1) {
            nextLast =0;
        } else {
            nextLast += 1;
        }

        size += 1;
    }

    /**
     * Check if the list is Empty.
     * @return True if the size of the linked list is 0.
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**
     * Return the number of items in the linked list.
     * @return int, size of the linked list.
     */
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    public void printDeque() {
        int first;
        int last;
        if (nextFirst == capacity - 1) {
            first = 0;
        } else {
            first = nextFirst + 1;
        }

        if (nextLast == 0) {
            last = capacity - 1;
        } else {
            last = nextLast - 1;
        }

        if (isEmpty()) {
            System.out.println("The array is empty!");
        }

        if (first <= last) {
            for (int i = first; i <= last; i += 1) {
                String p = array[i] + " ";
                System.out.print(p);
            }
        } else {
            for (int i = first; i < capacity; i += 1) {
                if (array[i] == null) {
                    continue;
                } else {
                    System.out.print(array[i] + " ");
                }
            }

            for (int i = 0; i <= last; i += 1) {
                if (array[i] == null) {
                    continue;
                } else {
                    System.out.print(array[i] + " ");
                }
            }

        }
        System.out.println();

    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     */
    public T removeFirst() {
        int first;
        if (nextFirst == capacity - 1) {
            first = 0;
        } else {
            first = nextFirst + 1;
        }

        T res = get(first);
        array[first] = null;
        nextFirst = first;
        size -= 1;

        if (size/capacity < 0.25) {
            shrink((int) capacity/4);
        }

        return res;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     */
    public T removeLast() {
        int last;
        if (nextLast == 0) {
            last = capacity - 1;
        } else {
            last = nextLast - 1;
        }

        T res = get(last);
        array[last] = null;
        nextFirst = last;
        size -= 1;

        if (size/capacity < 0.25) {
            shrink((int) capacity/4);
        }

        return res;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!
     * @param index
     * @return T item
     */
    public T get(int index) {
        return array[index];
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFirst() {
        return nextFirst;
    }

    public int getLast() {
        return nextLast;
    }
}
